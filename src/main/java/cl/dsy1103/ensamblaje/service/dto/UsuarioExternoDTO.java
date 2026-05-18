package cl.dsy1103.ensamblaje.service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioExternoDTO {
    private Long idUsuario;
    private String nombreCompleto;
    private String rol; // Para verificar que sea "TECNICO"
}