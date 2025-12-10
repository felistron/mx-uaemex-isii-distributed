package mx.uaemex.fi.backend.persistence.repository;

import lombok.NonNull;
import mx.uaemex.fi.backend.persistence.model.Acceso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccesoRepository extends JpaRepository<@NonNull Acceso, @NonNull Integer> {
    Optional<Acceso> findByIdEmpleado(Integer idEmpleado);
}
