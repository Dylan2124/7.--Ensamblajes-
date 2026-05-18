package cl.dsy1103.ensamblaje.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoExternoDTO {
    private Long idPedido;
    private Long idUsuario;
    private String estado;
}