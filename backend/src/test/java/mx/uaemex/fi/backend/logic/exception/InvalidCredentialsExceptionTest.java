package mx.uaemex.fi.backend.logic.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para InvalidCredentialsException.
 * Verifica el correcto funcionamiento de la excepción personalizada.
 */
@DisplayName("Pruebas unitarias de InvalidCredentialsException")
class InvalidCredentialsExceptionTest {

    @Test
    @DisplayName("Debe crear excepción con mensaje")
    void debeCrearExcepcionConMensaje() {
        // Arrange
        String mensaje = "Credenciales inválidas";

        // Act
        InvalidCredentialsException exception = new InvalidCredentialsException(mensaje);

        // Assert
        assertNotNull(exception);
        assertEquals(mensaje, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Debe lanzar excepción correctamente")
    void debeLanzarExcepcionCorrectamente() {
        // Arrange
        String mensaje = "Correo o contraseña incorrectos";

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> {
                    throw new InvalidCredentialsException(mensaje);
                }
        );

        assertEquals(mensaje, exception.getMessage());
    }

    @Test
    @DisplayName("Debe ser capturada como RuntimeException")
    void debeSerCapturadaComoRuntimeException() {
        // Arrange
        String mensaje = "Contraseña incorrecta";

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> {
                    throw new InvalidCredentialsException(mensaje);
                }
        );

        assertInstanceOf(InvalidCredentialsException.class, exception);
        assertEquals(mensaje, exception.getMessage());
    }
}

