package mx.uaemex.fi.backend.logic.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para NotFoundException.
 * Verifica el correcto funcionamiento de la excepción personalizada.
 */
@DisplayName("Pruebas unitarias de NotFoundException")
class NotFoundExceptionTest {

    @Test
    @DisplayName("Debe crear excepción con mensaje")
    void debeCrearExcepcionConMensaje() {
        // Arrange
        String mensaje = "Empleado no encontrado con RFC: ABCD123456XYZ";

        // Act
        NotFoundException exception = new NotFoundException(mensaje);

        // Assert
        assertNotNull(exception);
        assertEquals(mensaje, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Debe lanzar excepción correctamente")
    void debeLanzarExcepcionCorrectamente() {
        // Arrange
        String mensaje = "Recurso no encontrado";

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> {
                    throw new NotFoundException(mensaje);
                }
        );

        assertEquals(mensaje, exception.getMessage());
    }

    @Test
    @DisplayName("Debe ser capturada como RuntimeException")
    void debeSerCapturadaComoRuntimeException() {
        // Arrange
        String mensaje = "Nómina no encontrada";

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    throw new NotFoundException(mensaje);
                }
        );

        assertInstanceOf(NotFoundException.class, exception);
        assertEquals(mensaje, exception.getMessage());
    }
}

