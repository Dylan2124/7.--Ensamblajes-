package cl.dsy1103.ensamblaje.service.repository;

import cl.dsy1103.ensamblaje.service.model.FaseEnsamblaje;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface FaseEnsamblajeRepository extends JpaRepository<FaseEnsamblaje, Long> {

    // Buscar todas las fases que pertenecen a un mismo ticket de ensamblaje.
    List<FaseEnsamblaje> findByIdTicket(Long idTicket);
}