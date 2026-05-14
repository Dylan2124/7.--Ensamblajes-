package cl.dsy1103.ensamblaje.service.service;

import cl.dsy1103.ensamblaje.service.dto.EnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.EnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.model.Ensamblaje;
import cl.dsy1103.ensamblaje.service.repository.EnsamblajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EnsamblajeService {

    private final EnsamblajeRepository ensamblajeRepository;

    // Entidad -> ResponseDTO
    private EnsamblajeResponseDTO mapToDTO(Ensamblaje e) {
        return new EnsamblajeResponseDTO(
                e.getIdTicket(),
                e.getIdPedido(),
                e.getIdTecnico(),
                e.getEstadoArmado(),
                e.getFechaInicio()
        );
    }

    // Listar todos los tickets
    public List<EnsamblajeResponseDTO> obtenerTodos() {
        return ensamblajeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un ticket por ID
    public Optional<EnsamblajeResponseDTO> obtenerPorId(Long id) {
        return ensamblajeRepository.findById(id).map(this::mapToDTO);
    }

    // Crear un nuevo ticket (Estado inicial: PENDIENTE)
    public EnsamblajeResponseDTO guardar(EnsamblajeRequestDTO dto) {
        Ensamblaje nuevo = new Ensamblaje();
        nuevo.setIdPedido(dto.getIdPedido());
        nuevo.setIdTecnico(dto.getIdTecnico());
        nuevo.setEstadoArmado("PENDIENTE");

        return mapToDTO(ensamblajeRepository.save(nuevo));
    }

    // Iniciar el trabajo (Cambia estado y pone fecha/hora actual)
    public Optional<EnsamblajeResponseDTO> iniciarTrabajo(Long idTicket) {
        return ensamblajeRepository.findById(idTicket).map(existente -> {
            existente.setEstadoArmado("EN_PROCESO");
            existente.setFechaInicio(LocalDateTime.now());
            return mapToDTO(ensamblajeRepository.save(existente));
        });
    }
}