package cl.dsy1103.ensamblaje.service.controller;

import cl.dsy1103.ensamblaje.service.dto.EnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.EnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.service.EnsamblajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ensamblajes")
@RequiredArgsConstructor
public class EnsamblajeController {

    private final EnsamblajeService ensamblajeService;

    // Obtener todos los tickets
    @GetMapping
    public ResponseEntity<List<EnsamblajeResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(ensamblajeService.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<EnsamblajeResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ensamblajeService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un ticket (POST)
    @PostMapping
    public ResponseEntity<EnsamblajeResponseDTO> crear(@Valid @RequestBody EnsamblajeRequestDTO dto) {
        return ResponseEntity.status(201).body(ensamblajeService.guardar(dto));
    }

    // Iniciar trabajo (PUT) - Cambia estado a EN PROCESO
    @PutMapping("/{id}/iniciar")
    public ResponseEntity<EnsamblajeResponseDTO> iniciar(@PathVariable Long id) {
        return ensamblajeService.iniciarTrabajo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
