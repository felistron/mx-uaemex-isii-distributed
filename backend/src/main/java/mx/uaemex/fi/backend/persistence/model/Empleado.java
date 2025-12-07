package mx.uaemex.fi.backend.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "empleado", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "empleado_rfc_key", columnNames = {"rfc"}),
        @UniqueConstraint(name = "empleado_correo_key", columnNames = {"correo"})
})
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empleado_id_gen")
    @SequenceGenerator(name = "empleado_id_gen", sequenceName = "empleado_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 13)
    @NotNull
    @Column(name = "rfc", nullable = false, length = 13)
    private String rfc;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombres", nullable = false, length = 50)
    private String nombres;

    @Size(max = 50)
    @NotNull
    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellidos;

    @Size(max = 100)
    @NotNull
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

}