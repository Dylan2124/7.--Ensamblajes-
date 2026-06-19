package cl.dsy1103.ensamblaje.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnsamblajeResponseDTO extends RepresentationModel<EnsamblajeResponseDTO> {
    private Long idTicket;
    private Long idPedido;
    private Long idTecnico;
    private String estadoArmado;
    private LocalDateTime fechaInicio;
}