package cl.dsy1103.ensamblaje.service.service;

import cl.dsy1103.ensamblaje.service.dto.FaseEnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.FaseEnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.model.FaseEnsamblaje;
import cl.dsy1103.ensamblaje.service.repository.FaseEnsamblajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaseEnsamblajeServiceTest {

    @Mock private FaseEnsamblajeRepository repository;
    @InjectMocks private FaseEnsamblajeService service;

    private FaseEnsamblaje fase;
    private FaseEnsamblajeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        fase = new FaseEnsamblaje();
        fase.setIdFase(1L);
        fase.setIdTicket(100L);
        fase.setNombreFase("Armado Base");
        fase.setCompletada(false);

        requestDTO = new FaseEnsamblajeRequestDTO();
        requestDTO.setIdTicket(100L);
        requestDTO.setNombreFase("Armado Base");
    }

    @Test
    void obtenerTodas() {
        when(repository.findAll()).thenReturn(Arrays.asList(fase));
        assertFalse(service.obtenerTodas().isEmpty());
    }

    @Test
    void obtenerPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(fase));
        assertTrue(service.obtenerPorId(1L).isPresent());
    }

    @Test
    void guardar() {
        when(repository.save(any(FaseEnsamblaje.class))).thenReturn(fase);
        FaseEnsamblajeResponseDTO res = service.guardar(requestDTO);
        assertNotNull(res);
        assertFalse(res.getCompletada());
    }

    @Test
    void marcarComoCompletada() {
        when(repository.findById(1L)).thenReturn(Optional.of(fase));
        when(repository.save(any(FaseEnsamblaje.class))).thenReturn(fase);

        Optional<FaseEnsamblajeResponseDTO> res = service.marcarComoCompletada(1L);
        assertTrue(res.isPresent());
        assertTrue(res.get().getCompletada());
    }

    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);
        service.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}