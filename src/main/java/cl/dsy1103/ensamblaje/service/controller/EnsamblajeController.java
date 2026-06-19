package cl.dsy1103.ensamblaje.service.controller;

import cl.dsy1103.ensamblaje.service.dto.EnsamblajeRequestDTO;
import cl.dsy1103.ensamblaje.service.dto.EnsamblajeResponseDTO;
import cl.dsy1103.ensamblaje.service.service.EnsamblajeService;
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
@RequestMapping("/api/ensamblajes")
@RequiredArgsConstructor
public class EnsamblajeController {

    private final EnsamblajeService service;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<EnsamblajeResponseDTO> lista = service.obtenerTodos();
        if (lista.isEmpty()) return ResponseEntity.ok(Map.of("mensaje", "No hay tickets de ensamblaje"));

        lista.forEach(dto -> dto.add(linkTo(methodOn(EnsamblajeController.class).obtenerPorId(dto.getIdTicket())).withSelfRel()));
        CollectionModel<EnsamblajeResponseDTO> collectionModel = CollectionModel.of(lista,
                linkTo(methodOn(EnsamblajeController.class).obtenerTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<EnsamblajeResponseDTO> ticket = service.obtenerPorId(id);
        if (ticket.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se encontró ticket con ID: " + id));
        }

        EnsamblajeResponseDTO response = ticket.get();
        response.add(linkTo(methodOn(EnsamblajeController.class).obtenerPorId(id)).withSelfRel());
        response.add(linkTo(methodOn(EnsamblajeController.class).obtenerTodos()).withRel("todos-los-tickets"));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EnsamblajeResponseDTO> guardar(@Valid @RequestBody EnsamblajeRequestDTO dto) {
        EnsamblajeResponseDTO response = service.guardar(dto);
        response.add(linkTo(methodOn(EnsamblajeController.class).obtenerPorId(response.getIdTicket())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<?> iniciarTrabajo(@PathVariable Long id) {
        Optional<EnsamblajeResponseDTO> actualizado = service.iniciarTrabajo(id);
        if (actualizado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se puede iniciar. No existe ticket con ID: " + id));
        }

        EnsamblajeResponseDTO response = actualizado.get();
        response.add(linkTo(methodOn(EnsamblajeController.class).obtenerPorId(id)).withSelfRel());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No se puede eliminar. No existe ticket con ID: " + id));
        }
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Ticket con ID " + id + " eliminado con éxito."));
    }
}