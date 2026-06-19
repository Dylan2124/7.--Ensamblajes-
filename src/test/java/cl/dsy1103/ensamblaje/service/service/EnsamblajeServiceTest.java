package cl.dsy1103.ensamblaje.service.service;

import cl.dsy1103.ensamblaje.service.client.NotificacionClient;
import cl.dsy1103.ensamblaje.service.client.PedidoClient;
import cl.dsy1103.ensamblaje.service.client.UsuarioClient;
import cl.dsy1103.ensamblaje.service.dto.EnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.EnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.dto.NotificacionRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.PedidoExternoDTO;
import cl.dsy1103.ensamblaje.service.dto.UsuarioExternoDTO;
import cl.dsy1103.ensamblaje.service.model.Ensamblaje;
import cl.dsy1103.ensamblaje.service.repository.EnsamblajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnsamblajeServiceTest {

    @Mock private EnsamblajeRepository repository;
    @Mock private PedidoClient pedidoClient;
    @Mock private UsuarioClient usuarioClient;
    @Mock private NotificacionClient notificacionClient;

    @InjectMocks private EnsamblajeService service;

    private Ensamblaje ensamblaje;
    private EnsamblajeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ensamblaje = new Ensamblaje();
        ensamblaje.setIdTicket(1L);
        ensamblaje.setIdPedido(100L);
        ensamblaje.setIdTecnico(50L);
        ensamblaje.setEstadoArmado("PENDIENTE");

        requestDTO = new EnsamblajeRequestDTO();
        requestDTO.setIdPedido(100L);
        requestDTO.setIdTecnico(50L);
    }

    @Test
    void obtenerTodos() {
        when(repository.findAll()).thenReturn(Arrays.asList(ensamblaje));
        assertFalse(service.obtenerTodos().isEmpty());
    }

    @Test
    void obtenerPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(ensamblaje));
        assertTrue(service.obtenerPorId(1L).isPresent());
    }

    @Test
    void guardarConExito() {
        UsuarioExternoDTO user = new UsuarioExternoDTO();
        user.setNombreCompleto("Tecnico Master");

        PedidoExternoDTO pedido = new PedidoExternoDTO();
        pedido.setIdUsuario(99L);

        when(usuarioClient.obtenerUsuarioPorId(50L)).thenReturn(user);
        when(pedidoClient.obtenerPedidoPorId(100L)).thenReturn(pedido);
        when(repository.save(any(Ensamblaje.class))).thenReturn(ensamblaje);

        EnsamblajeResponseDTO res = service.guardar(requestDTO);
        assertNotNull(res);
        assertEquals("PENDIENTE", res.getEstadoArmado());
        verify(notificacionClient, times(1)).enviarNotificacion(any(NotificacionRequestDTO.class));
    }

    @Test
    void iniciarTrabajo() {
        when(repository.findById(1L)).thenReturn(Optional.of(ensamblaje));
        when(repository.save(any(Ensamblaje.class))).thenReturn(ensamblaje);

        Optional<EnsamblajeResponseDTO> res = service.iniciarTrabajo(1L);
        assertTrue(res.isPresent());
        assertEquals("EN_PROCESO", res.get().getEstadoArmado());
    }

    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);
        service.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}