package mx.uaemex.fi.backend.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import mx.uaemex.fi.backend.logic.validation.Periodo;
import mx.uaemex.fi.backend.logic.validation.RFCExists;

import java.time.LocalDate;

@Periodo
public record NominaRequest(
        @NotNull(message = "El RFC es obligatorio")
        @RFCExists
        String rfc,
        @NotNull(message = "El salario bruto es obligatorio")
        @DecimalMin(message = "El salario bruto debe ser mayor a 0.01", value = "0.01")
        Double salario,
        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate fechaInicio,
        @NotNull(message = "La fecha de fin es obligatoria")
        LocalDate fechaFin
) {
}
