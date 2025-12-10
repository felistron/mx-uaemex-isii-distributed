package mx.uaemex.fi.backend.persistence.repository;

import lombok.NonNull;
import mx.uaemex.fi.backend.persistence.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<@NonNull Empleado, @NonNull Integer> {
    Optional<Empleado> findByCorreo(String correo);
    Optional<Empleado> findByRfc(String rfc);

    boolean existsByRfc(String rfc);

    boolean existsByCorreo(String correo);
}
