package mx.uaemex.fi.backend.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para LoginRequest.
 * Verifica el correcto funcionamiento del record LoginRequest.
 */
@DisplayName("Pruebas unitarias de LoginRequest")
class LoginRequestTest {

    @Test
    @DisplayName("Debe crear LoginRequest con valores válidos")
    void debeCrearLoginRequestConValoresValidos() {
        // Arrange
        String correo = "usuario@example.com";
        String password = "Password123!";

        // Act
        LoginRequest request = new LoginRequest(correo, password);

        // Assert
        assertNotNull(request);
        assertEquals(correo, request.correo());
        assertEquals(password, request.password());
    }

    @Test
    @DisplayName("Debe verificar equals y hashCode")
    void debeVerificarEqualsYHashCode() {
        // Arrange
        LoginRequest request1 = new LoginRequest("usuario@example.com", "Password123!");
        LoginRequest request2 = new LoginRequest("usuario@example.com", "Password123!");
        LoginRequest request3 = new LoginRequest("otro@example.com", "OtherPass456!");

        // Assert
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}

