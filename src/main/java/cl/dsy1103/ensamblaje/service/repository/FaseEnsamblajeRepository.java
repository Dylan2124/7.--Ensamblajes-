package cl.dsy1103.ensamblaje.service.repository;

import cl.dsy1103.ensamblaje.service.model.FaseEnsamblaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaseEnsamblajeRepository extends JpaRepository<FaseEnsamblaje, Long> {

    // Buscar todas las fases que pertenecen a un mismo ticket de ensamblaje.
    List<FaseEnsamblaje> findByIdTicket(Long idTicket);
}