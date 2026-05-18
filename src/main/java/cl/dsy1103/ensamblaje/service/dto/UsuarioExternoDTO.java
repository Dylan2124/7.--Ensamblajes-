package cl.dsy1103.ensamblaje.service.dto;


import lombok.Data;

@Data
public class UsuarioExternoDTO {
    private Long idUsuario;
    private String nombreCompleto;
    private String rol; // Para verificar que sea "TECNICO"
}