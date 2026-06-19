package cl.dsy1103.ensamblaje.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaseEnsamblajeResponseDTO extends RepresentationModel<FaseEnsamblajeResponseDTO> {
    private Long idFase;
    private Long idTicket;
    private String nombreFase;
    private Boolean completada;
}