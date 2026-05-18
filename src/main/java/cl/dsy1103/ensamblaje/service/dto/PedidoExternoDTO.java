package cl.dsy1103.ensamblaje.service.dto;

import lombok.Data;

@Data
public class PedidoExternoDTO {
    private Long idPedido;
    private Long idUsuario;
    private String estado;
}