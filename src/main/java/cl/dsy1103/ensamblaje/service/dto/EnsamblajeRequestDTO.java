package cl.dsy1103.ensamblaje.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnsamblajeRequestDTO {

    // Solo pedimos lo esencial para iniciar un ticket.
    // El ID lo da MySQL, el Estado y la Fecha los pone el Service.

    @NotNull (message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser mayor a 0")
    private Long idPedido;

    @NotNull(message = "El ID del técnico es obligatorio")
    @Positive(message = "El ID del técnico debe ser mayor a 0")
    private Long idTecnico;
}