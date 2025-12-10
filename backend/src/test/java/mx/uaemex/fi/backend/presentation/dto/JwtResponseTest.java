package mx.uaemex.fi.backend.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para JwtResponse.
 * Verifica el correcto funcionamiento del record JwtResponse.
 */
@DisplayName("Pruebas unitarias de JwtResponse")
class JwtResponseTest {

    @Test
    @DisplayName("Debe crear JwtResponse con valores válidos")
    void debeCrearJwtResponseConValoresValidos() {
        // Arrange
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U";
        String type = "Bearer";
        Long expiresIn = 3600L;

        // Act
        JwtResponse response = new JwtResponse(token, type, expiresIn);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.token());
        assertEquals(type, response.type());
        assertEquals(expiresIn, response.expiresInMs());
    }

    @Test
    @DisplayName("Debe verificar equals y hashCode")
    void debeVerificarEqualsYHashCode() {
        // Arrange
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        JwtResponse response1 = new JwtResponse(token, "Bearer", 3600L);
        JwtResponse response2 = new JwtResponse(token, "Bearer", 3600L);
        JwtResponse response3 = new JwtResponse("differentToken", "Bearer", 7200L);

        // Assert
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}

