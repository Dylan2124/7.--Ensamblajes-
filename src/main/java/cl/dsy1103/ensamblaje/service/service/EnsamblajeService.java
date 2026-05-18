package cl.dsy1103.ensamblaje.service.service;

import cl.dsy1103.ensamblaje.service.dto.*;
import cl.dsy1103.ensamblaje.service.model.Ensamblaje;
import cl.dsy1103.ensamblaje.service.repository.EnsamblajeRepository;

import cl.dsy1103.ensamblaje.service.client.PedidoClient;
import cl.dsy1103.ensamblaje.service.client.UsuarioClient;
import cl.dsy1103.ensamblaje.service.client.NotificacionClient;
import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnsamblajeService {

    private final EnsamblajeRepository ensamblajeRepository;

    private final PedidoClient pedidoClient;
    private final UsuarioClient usuarioClient;
    private final NotificacionClient notificacionClient;

    private EnsamblajeResponseDTO mapToDTO(Ensamblaje e) {
        return new EnsamblajeResponseDTO(
                e.getIdTicket(), e.getIdPedido(), e.getIdTecnico(),
                e.getEstadoArmado(), e.getFechaInicio()
        );
    }

    public List<EnsamblajeResponseDTO> obtenerTodos() {
        return ensamblajeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<EnsamblajeResponseDTO> obtenerPorId(Long id) {
        return ensamblajeRepository.findById(id).map(this::mapToDTO);
    }

    public EnsamblajeResponseDTO guardar(EnsamblajeRequestDTO dto) {

        PedidoExternoDTO datosPedido = null;

        try {
            log.info("Llamando a ms-usuarios para validar al técnico ID: {}", dto.getIdTecnico());
            UsuarioExternoDTO tecnico = usuarioClient.obtenerUsuarioPorId(dto.getIdTecnico());
            log.info("Técnico validado: {}", tecnico.getNombreCompleto());

            log.info("Llamando a ms-pedidos para rescatar al dueño del pedido ID: {}", dto.getIdPedido());
            datosPedido = pedidoClient.obtenerPedidoPorId(dto.getIdPedido());
            log.info("Dueño rescatado. ID Usuario: {}", datosPedido.getIdUsuario());
        } catch (Exception e) {
            // Tolerancia a fallos: Si Pedidos o Usuarios están caídos, registramos el error
            // pero el código NO se cae, sigue de largo para guardar el ticket igual.
            log.warn("TOLERANCIA A FALLOS: No se pudo conectar con ms-usuarios o ms-pedidos. Se continuará con el guardado local. Motivo: {}", e.getMessage());
        }

        Ensamblaje nuevo = new Ensamblaje();
        nuevo.setIdPedido(dto.getIdPedido());
        nuevo.setIdTecnico(dto.getIdTecnico());
        nuevo.setEstadoArmado("PENDIENTE");

        Ensamblaje guardado = ensamblajeRepository.save(nuevo);
        log.info("Ticket de ensamblaje guardado correctamente en la BD local.");

        if (datosPedido != null && datosPedido.getIdUsuario() != null) {
            try {
                log.info("Llamando a ms-notificaciones para avisar al cliente...");

                NotificacionRequestDTO aviso = new NotificacionRequestDTO(
                        datosPedido.getIdUsuario(),
                        guardado.getIdPedido(),
                        "INFO_ENSAMBLAJE",
                        "¡Buenas noticias! Tu pedido ha sido asignado a un técnico y está en cola para ser ensamblado."
                );

                notificacionClient.enviarNotificacion(aviso);
                log.info("¡Notificación enviada con éxito!");
            } catch (Exception e) {
                log.warn("TOLERANCIA A FALLOS: El ticket se guardó, pero no se pudo enviar el correo de aviso: {}", e.getMessage());
            }
        } else {
            log.warn("Se omitió el envío a ms-notificaciones porque no se pudo rescatar el ID del cliente desde ms-pedidos.");
        }

        return mapToDTO(guardado);
    }

    public Optional<EnsamblajeResponseDTO> iniciarTrabajo(Long idTicket) {
        return ensamblajeRepository.findById(idTicket).map(existente -> {
            existente.setEstadoArmado("EN_PROCESO");
            existente.setFechaInicio(LocalDateTime.now());
            return mapToDTO(ensamblajeRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        ensamblajeRepository.deleteById(id);
    }
}