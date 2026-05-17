package cl.dsy1103.ensamblaje.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaseEnsamblajeResponseDTO {
    private Long idFase;
    private Long idTicket;
    private String nombreFase;
    private Boolean completada;
}