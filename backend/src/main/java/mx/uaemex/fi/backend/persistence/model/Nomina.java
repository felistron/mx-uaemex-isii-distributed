package mx.uaemex.fi.backend.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "nomina", schema = "public")
public class Nomina {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nomina_id_gen")
    @SequenceGenerator(name = "nomina_id_gen", sequenceName = "nomina_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @NotNull
    @Column(name = "salario_bruto", nullable = false)
    private Double salarioBruto;

    @NotNull
    @Column(name = "excedente", nullable = false)
    private Double excedente;

    @NotNull
    @Column(name = "cuota_fija", nullable = false)
    private Double cuotaFija;

    @NotNull
    @Column(name = "porcentaje", nullable = false)
    private Double porcentaje;

    @NotNull
    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @NotNull
    @Column(name = "periodo_fin", nullable = false)
    private LocalDate periodoFin;

}