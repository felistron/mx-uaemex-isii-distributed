package mx.uaemex.fi.backend.persistence.repository;

import lombok.NonNull;
import mx.uaemex.fi.backend.persistence.model.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NominaRepository extends JpaRepository<@NonNull Nomina, @NonNull Integer> {
    List<Nomina> findAllByEmpleado_RfcOrderByPeriodoInicio(String empleadoRfc);
}
