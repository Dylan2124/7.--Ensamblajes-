package cl.dsy1103.ensamblaje.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnsamblajeResponseDTO {
    private Long idTicket;
    private Long idPedido;
    private Long idTecnico;
    private String estadoArmado;
    private LocalDateTime fechaInicio;
}