package cl.dsy1103.ensamblaje.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaseEnsamblajeRequestDTO {

    @NotNull(message = "El ID del ticket de ensamblaje es obligatorio")
    @Positive(message = "El ID del ticket debe ser mayor a 0")
    private Long idTicket;

    @NotBlank(message = "El nombre de la fase no puede estar vacío")
    private String nombreFase;
}