package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.ConstraintValidatorContext;
import mx.uaemex.fi.backend.presentation.dto.NominaRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para PeriodoValidator
 * Verifica que la fecha de inicio sea anterior a la fecha de fin
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PeriodoValidator - Validación de periodo de nómina")
class PeriodoValidatorTest {

    private PeriodoValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new PeriodoValidator();
    }

    @Test
    @DisplayName("isValid - Fecha inicio antes de fecha fin retorna true")
    void isValid_fechaInicioAntesDeFechaFin_retornaTrue() {
        // Arrange
        NominaRequest request = new NominaRequest(
                "AAAA012345XXX",
                5000.00,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 15)
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertTrue(resultado, "Un periodo válido (inicio < fin) debe ser válido");
    }

    @Test
    @DisplayName("isValid - Fecha inicio después de fecha fin retorna false")
    void isValid_fechaInicioDespuesDeFechaFin_retornaFalse() {
        // Arrange
        NominaRequest request = new NominaRequest(
                "AAAA012345XXX",
                5000.00,
                LocalDate.of(2025, 1, 15),
                LocalDate.of(2025, 1, 1)
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Un periodo inválido (inicio > fin) debe ser inválido");
    }

    @Test
    @DisplayName("isValid - Fechas iguales retorna false")
    void isValid_fechasIguales_retornaFalse() {
        // Arrange
        LocalDate fecha = LocalDate.of(2025, 1, 15);
        NominaRequest request = new NominaRequest(
                "AAAA012345XXX",
                5000.00,
                fecha,
                fecha
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Fechas iguales deben ser inválidas (el periodo debe ser un rango)");
    }

    @Test
    @DisplayName("isValid - Fecha inicio null retorna true")
    void isValid_fechaInicioNull_retornaTrue() {
        // Arrange
        NominaRequest request = new NominaRequest(
                "AAAA012345XXX",
                5000.00,
                null,
                LocalDate.of(2025, 1, 15)
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertTrue(resultado, "Fecha inicio null debe ser válido");
    }

    @Test
    @DisplayName("isValid - Fecha fin null retorna true")
    void isValid_fechaFinNull_retornaTrue() {
        // Arrange
        NominaRequest request = new NominaRequest(
                "AAAA012345XXX",
                5000.00,
                LocalDate.of(2025, 1, 1),
                null
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertTrue(resultado, "Fecha fin null debe ser válido");
    }
}

