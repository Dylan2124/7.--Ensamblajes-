package cl.dsy1103.ensamblaje.service.config;

import cl.dsy1103.ensamblaje.service.model.Ensamblaje;
import cl.dsy1103.ensamblaje.service.model.FaseEnsamblaje;
import cl.dsy1103.ensamblaje.service.repository.EnsamblajeRepository;
import cl.dsy1103.ensamblaje.service.repository.FaseEnsamblajeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EnsamblajeRepository ensamblajeRepository;
    private final FaseEnsamblajeRepository faseRepository;

    @Override
    public void run(String... args) {

        // ── GUARDIA PRINCIPAL ────────────────────────
        // Si ya existen categorías en la BD, no hacemos nada.
        // Esto evita duplicar datos en cada reinicio del servidor.
        // Si un estudiante borra una categoría desde phpMyAdmin
        // pero quedan otras, el inicializador NO actúa.
        // Para "resetear", vaciar la BD manualmente y reiniciar.

        if (ensamblajeRepository.count() > 0) {
            log.info(">>> La BD ya tiene datos de ensamblaje, saltando carga inicial.");
            return;
        }

        log.info(">>> Insertando datos de prueba para Ensamblaje...");

        // Tickets de prueba
        Ensamblaje e1 = ensamblajeRepository.save(new Ensamblaje(null, 101L, 1L, "EN PROCESO", java.time.LocalDateTime.now()));
        Ensamblaje e2 = ensamblajeRepository.save(new Ensamblaje(null, 102L, 2L, "PENDIENTE", null));

        // Fases para el primer ticket
        faseRepository.save(new FaseEnsamblaje(null, e1.getIdTicket(), "Montaje de Placa Madre", true));
        faseRepository.save(new FaseEnsamblaje(null, e1.getIdTicket(), "Instalación de Fuente de Poder", false));

        log.info(">>> Datos iniciales cargados: 2 tickets y 2 fases creadas.");
    }
}