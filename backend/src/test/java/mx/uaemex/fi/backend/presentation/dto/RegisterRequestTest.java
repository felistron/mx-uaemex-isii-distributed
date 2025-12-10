package mx.uaemex.fi.backend.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para RegisterRequest
 * Verifica la creación correcta del DTO de registro
 * Nota: Las validaciones con anotaciones custom (@UniqueRFC, @UniqueEmail, @ConditionalPassword)
 * se prueban en los tests de controladores donde hay contexto de Spring
 */
@DisplayName("RegisterRequest - DTO de registro")
class RegisterRequestTest {

    @Test
    @DisplayName("record - Empleado normal se crea correctamente")
    void record_empleadoNormal_seCreaCorrectamente() {
        // Arrange & Act
        RegisterRequest request = new RegisterRequest(
                "AAAA012345XXX",
                "JUAN",
                "PEREZ LOPEZ",
                "juan@test.com",
                false,
                null,
                null
        );

        // Assert
        assertEquals("AAAA012345XXX", request.rfc());
        assertEquals("JUAN", request.nombre());
        assertEquals("PEREZ LOPEZ", request.apellidos());
        assertEquals("juan@test.com", request.correo());
        assertFalse(request.esAdministrador());
        assertNull(request.password());
        assertNull(request.confirmPassword());
    }

    @Test
    @DisplayName("record - Administrador se crea correctamente")
    void record_administrador_seCreaCorrectamente() {
        // Arrange & Act
        RegisterRequest request = new RegisterRequest(
                "AAAA012345XXX",
                "JUAN",
                "PEREZ LOPEZ",
                "juan@test.com",
                true,
                "Qwertyuiop1*",
                "Qwertyuiop1*"
        );

        // Assert
        assertEquals("AAAA012345XXX", request.rfc());
        assertEquals("JUAN", request.nombre());
        assertEquals("PEREZ LOPEZ", request.apellidos());
        assertEquals("juan@test.com", request.correo());
        assertTrue(request.esAdministrador());
        assertEquals("Qwertyuiop1*", request.password());
        assertEquals("Qwertyuiop1*", request.confirmPassword());
    }
}

