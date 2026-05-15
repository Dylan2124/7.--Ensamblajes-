package cl.dsy1103.ensamblaje.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_ensamblaje")
public class Ensamblaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_tecnico")
    private Long idTecnico;

    @Column(name = "estado_armado")
    private String estadoArmado;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
}
