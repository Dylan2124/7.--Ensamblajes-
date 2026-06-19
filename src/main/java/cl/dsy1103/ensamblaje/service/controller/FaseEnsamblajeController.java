package cl.dsy1103.ensamblaje.service.controller;

import cl.dsy1103.ensamblaje.service.dto.FaseEnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.FaseEnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.service.FaseEnsamblajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/fases")
@RequiredArgsConstructor
public class FaseEnsamblajeController {

    private final FaseEnsamblajeService service;

    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        List<FaseEnsamblajeResponseDTO> lista = service.obtenerTodas();
        if (lista.isEmpty()) return ResponseEntity.ok(Map.of("mensaje", "No hay fases registradas"));

        lista.forEach(dto -> dto.add(linkTo(methodOn(FaseEnsamblajeController.class).obtenerPorId(dto.getIdFase())).withSelfRel()));
        CollectionModel<FaseEnsamblajeResponseDTO> collectionModel = CollectionModel.of(lista,
                linkTo(methodOn(FaseEnsamblajeController.class).obtenerTodas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<FaseEnsamblajeResponseDTO> fase = service.obtenerPorId(id);
        if (fase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se encontró fase con ID: " + id));
        }

        FaseEnsamblajeResponseDTO response = fase.get();
        response.add(linkTo(methodOn(FaseEnsamblajeController.class).obtenerPorId(id)).withSelfRel());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FaseEnsamblajeResponseDTO> guardar(@Valid @RequestBody FaseEnsamblajeRequestDTO dto) {
        FaseEnsamblajeResponseDTO response = service.guardar(dto);
        response.add(linkTo(methodOn(FaseEnsamblajeController.class).obtenerPorId(response.getIdFase())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<?> marcarCompletada(@PathVariable Long id) {
        Optional<FaseEnsamblajeResponseDTO> actualizada = service.marcarComoCompletada(id);
        if (actualizada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se puede completar. No existe fase con ID: " + id));
        }

        FaseEnsamblajeResponseDTO response = actualizada.get();
        response.add(linkTo(methodOn(FaseEnsamblajeController.class).obtenerPorId(id)).withSelfRel());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se puede eliminar. No existe fase con ID: " + id));
        }
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Fase con ID " + id + " eliminada con éxito."));
    }
}