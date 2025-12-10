package mx.uaemex.fi.backend.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para NominaRequest
 * Verifica la creación correcta del DTO de nómina
 * Nota: Las validaciones con anotaciones custom (@RFCExists, @Periodo)
 * se prueban en los tests de controladores donde hay contexto de Spring
 */
@DisplayName("NominaRequest - DTO de nómina")
class NominaRequestTest {

    @Test
    @DisplayName("record - Todos los campos se crean correctamente")
    void record_todosLosCampos_seCreaCorrectamente() {
        // Arrange & Act
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = LocalDate.of(2025, 1, 15);
        NominaRequest request = new NominaRequest(
                "AAAA012345XXX",
                5000.00d,
                inicio,
                fin
        );

        // Assert
        assertEquals("AAAA012345XXX", request.rfc());
        assertEquals(5000.00d, request.salario());
        assertEquals(inicio, request.fechaInicio());
        assertEquals(fin, request.fechaFin());
    }

    @Test
    @DisplayName("record - Valores diferentes se crean correctamente")
    void record_valoresDiferentes_seCreaCorrectamente() {
        // Arrange & Act
        LocalDate inicio = LocalDate.of(2025, 2, 1);
        LocalDate fin = LocalDate.of(2025, 2, 28);
        NominaRequest request = new NominaRequest(
                "BBBB987654YYY",
                10000.50d,
                inicio,
                fin
        );

        // Assert
        assertEquals("BBBB987654YYY", request.rfc());
        assertEquals(10000.50d, request.salario());
        assertEquals(inicio, request.fechaInicio());
        assertEquals(fin, request.fechaFin());
    }
}

