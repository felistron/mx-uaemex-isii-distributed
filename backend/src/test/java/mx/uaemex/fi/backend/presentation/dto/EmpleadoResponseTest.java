package mx.uaemex.fi.backend.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para EmpleadoResponse.
 * Verifica el correcto funcionamiento del record EmpleadoResponse.
 */
@DisplayName("Pruebas unitarias de EmpleadoResponse")
class EmpleadoResponseTest {

    @Test
    @DisplayName("Debe crear EmpleadoResponse con valores válidos")
    void debeCrearEmpleadoResponseConValoresValidos() {
        // Arrange
        Integer id = 1;
        String rfc = "ABCD123456XYZ";
        String nombre = "Juan";
        String apellidos = "Pérez García";
        String correo = "juan.perez@example.com";

        // Act
        EmpleadoResponse response = new EmpleadoResponse(id, rfc, nombre, apellidos, correo);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(rfc, response.rfc());
        assertEquals(nombre, response.nombre());
        assertEquals(apellidos, response.apellidos());
        assertEquals(correo, response.correo());
    }

    @Test
    @DisplayName("Debe verificar equals y hashCode")
    void debeVerificarEqualsYHashCode() {
        // Arrange
        EmpleadoResponse response1 = new EmpleadoResponse(
                1,
                "ABCD123456XYZ",
                "Juan",
                "Pérez García",
                "juan.perez@example.com"
        );

        EmpleadoResponse response2 = new EmpleadoResponse(
                1,
                "ABCD123456XYZ",
                "Juan",
                "Pérez García",
                "juan.perez@example.com"
        );

        EmpleadoResponse response3 = new EmpleadoResponse(
                2,
                "WXYZ987654ABC",
                "María",
                "González López",
                "maria.gonzalez@example.com"
        );

        // Assert
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}

