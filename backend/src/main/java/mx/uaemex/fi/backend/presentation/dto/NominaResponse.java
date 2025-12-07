package mx.uaemex.fi.backend.presentation.dto;

import java.time.LocalDate;

public record NominaResponse(
        Integer id,
        Double salario,
        Double excedente,
        Double cuotaFija,
        Double porcentaje,
        LocalDate periodoInicio,
        LocalDate periodoFin
) {
}
