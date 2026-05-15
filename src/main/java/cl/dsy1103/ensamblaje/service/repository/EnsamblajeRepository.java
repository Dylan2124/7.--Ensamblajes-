package cl.dsy1103.ensamblaje.service.repository;

import cl.dsy1103.ensamblaje.service.model.Ensamblaje;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface EnsamblajeRepository extends JpaRepository<Ensamblaje, Long> {

    // Buscar todos los ensamblajes (tickets) de un tecnico específico
    List<Ensamblaje> findByIdTecnico(Long idTecnico);

    // Buscar ensamblajes por su estado (ejemplos: "PENDIENTE", "ARMADO")
    List<Ensamblaje> findByEstadoArmado(String estadoArmado);
}