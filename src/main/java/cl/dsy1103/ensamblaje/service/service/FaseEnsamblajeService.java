package cl.dsy1103.ensamblaje.service.service;

import cl.dsy1103.ensamblaje.service.dto.FaseEnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.FaseEnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.model.FaseEnsamblaje;
import cl.dsy1103.ensamblaje.service.repository.FaseEnsamblajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaseEnsamblajeService {

    private final FaseEnsamblajeRepository faseRepository;

    private FaseEnsamblajeResponseDTO mapToDTO(FaseEnsamblaje f) {
        return new FaseEnsamblajeResponseDTO(
                f.getIdFase(), f.getIdTicket(), f.getNombreFase(), f.getCompletada()
        );
    }

    public List<FaseEnsamblajeResponseDTO> obtenerTodas() {
        return faseRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<FaseEnsamblajeResponseDTO> obtenerPorId(Long id) {
        return faseRepository.findById(id).map(this::mapToDTO);
    }

    // CREAR: Por defecto entra como NO completada (false)
    public FaseEnsamblajeResponseDTO guardar(FaseEnsamblajeRequestDTO dto) {
        FaseEnsamblaje nueva = new FaseEnsamblaje();
        nueva.setIdTicket(dto.getIdTicket());
        nueva.setNombreFase(dto.getNombreFase());
        nueva.setCompletada(false);
        return mapToDTO(faseRepository.save(nueva));
    }

    // MARCAR COMPLETADA: Cambia el boolean a true
    public Optional<FaseEnsamblajeResponseDTO> marcarComoCompletada(Long idFase) {
        return faseRepository.findById(idFase).map(existente -> {
            existente.setCompletada(true);
            return mapToDTO(faseRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        faseRepository.deleteById(id);
    }
}