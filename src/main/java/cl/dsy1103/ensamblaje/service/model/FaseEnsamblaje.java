package cl.dsy1103.ensamblaje.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Fase_ensamblaje")
public class FaseEnsamblaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fase")
    private Long idFase;

    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(name = "nombre_fase")
    private String nombreFase;

    @Column(name = "completada")
    private Boolean completada;
}
