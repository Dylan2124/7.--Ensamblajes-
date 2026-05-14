package cl.dsy1103.ensamblaje.service.controller;

import cl.dsy1103.ensamblaje.service.model.FaseEnsamblaje;
import cl.dsy1103.ensamblaje.service.service.FaseEnsamblajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fases")
@RequiredArgsConstructor
public class FaseEnsamblajeController {

    private final FaseEnsamblajeService faseService;

    @GetMapping
    public ResponseEntity<List<FaseEnsamblaje>> listarTodas() {
        return ResponseEntity.ok(faseService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<FaseEnsamblaje> guardar(@RequestBody FaseEnsamblaje fase) {
        return ResponseEntity.status(201).body(faseService.guardar(fase));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        faseService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}