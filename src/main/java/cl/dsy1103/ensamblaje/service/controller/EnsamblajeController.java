package cl.dsy1103.ensamblaje.service.controller;

import cl.dsy1103.ensamblaje.service.dto.EnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.EnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.service.EnsamblajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ensamblajes")
@RequiredArgsConstructor
public class EnsamblajeController {

    private final EnsamblajeService service;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<EnsamblajeResponseDTO> lista = service.obtenerTodos();
        if (lista.isEmpty()) return ResponseEntity.ok(Map.of("mensaje", "No hay tickets de ensamblaje"));
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<EnsamblajeResponseDTO> ticket = service.obtenerPorId(id);
        if (ticket.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró ticket con ID: " + id));
        }
        return ResponseEntity.ok(ticket.get());
    }

    @PostMapping
    public ResponseEntity<EnsamblajeResponseDTO> guardar(@Valid @RequestBody EnsamblajeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    // PUT especial para cambiar estado a "EN_PROCESO"
    @PutMapping("/{id}/iniciar")
    public ResponseEntity<?> iniciarTrabajo(@PathVariable Long id) {
        Optional<EnsamblajeResponseDTO> actualizado = service.iniciarTrabajo(id);
        if (actualizado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se puede iniciar. No existe ticket con ID: " + id));
        }
        return ResponseEntity.ok(actualizado.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se puede eliminar. No existe ticket con ID: " + id));
        }
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Ticket con ID " + id + " eliminado con éxito."));
    }
}