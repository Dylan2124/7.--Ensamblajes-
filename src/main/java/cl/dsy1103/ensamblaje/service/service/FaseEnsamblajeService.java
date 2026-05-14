package cl.dsy1103.ensamblaje.service.service; // Revisa que este sea tu paquete de service

import cl.dsy1103.ensamblaje.service.model.FaseEnsamblaje;
import cl.dsy1103.ensamblaje.service.repository.FaseEnsamblajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * * ═══════════════════════════════════════════════════
 * * CLASE: FaseEnsamblajeService.java
 * * ═══════════════════════════════════════════════════
 */
@Service
@RequiredArgsConstructor
public class FaseEnsamblajeService {

    private final FaseEnsamblajeRepository faseRepository;

    // SELECT * FROM fases
    public List<FaseEnsamblaje> obtenerTodas() {
        return faseRepository.findAll();
    }

    // SELECT * FROM fases WHERE id = ?
    public Optional<FaseEnsamblaje> obtenerPorId(Long id) {
        return faseRepository.findById(id);
    }

    // INSERT o UPDATE
    public FaseEnsamblaje guardar(FaseEnsamblaje fase) {
        return faseRepository.save(fase);
    }

    // DELETE
    public void eliminar(Long id) {
        faseRepository.deleteById(id);
    }
}