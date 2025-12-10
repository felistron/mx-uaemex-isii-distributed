package mx.uaemex.fi.backend.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "acceso", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "acceso_id_empleado_key", columnNames = {"id_empleado"})
})
public class Acceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "id_empleado", nullable = false)
    private Integer idEmpleado;

    @NotNull
    @Column(name = "hashed_password", nullable = false, length = Integer.MAX_VALUE)
    private String hashedPassword;

}