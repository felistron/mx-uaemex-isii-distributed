package mx.uaemex.fi.backend.persistence.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Empleado.
 * Verifica el correcto funcionamiento del modelo de dominio Empleado.
 */
@DisplayName("Pruebas unitarias de Empleado")
class EmpleadoTest {

    @Test
    @DisplayName("Debe crear empleado con builder")
    void debeCrearEmpleadoConBuilder() {
        // Arrange & Act
        Empleado empleado = new Empleado();
        empleado.setId(1);
        empleado.setRfc("ABCD123456XYZ");
        empleado.setNombres("Juan");
        empleado.setApellidos("Pérez García");
        empleado.setCorreo("juan.perez@example.com");

        // Assert
        assertNotNull(empleado);
        assertEquals(1, empleado.getId());
        assertEquals("ABCD123456XYZ", empleado.getRfc());
        assertEquals("Juan", empleado.getNombres());
        assertEquals("Pérez García", empleado.getApellidos());
        assertEquals("juan.perez@example.com", empleado.getCorreo());
    }

    @Test
    @DisplayName("Debe crear empleado con constructor vacío")
    void debeCrearEmpleadoConConstructorVacio() {
        // Arrange & Act
        Empleado empleado = new Empleado();

        // Assert
        assertNotNull(empleado);
        assertNull(empleado.getId());
        assertNull(empleado.getRfc());
        assertNull(empleado.getNombres());
        assertNull(empleado.getApellidos());
        assertNull(empleado.getCorreo());
    }

    @Test
    @DisplayName("Debe verificar equals")
    void debeVerificarEquals() {
        // Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setId(1);
        empleado1.setRfc("ABCD123456XYZ");
        empleado1.setNombres("Ana");
        empleado1.setApellidos("López Flores");
        empleado1.setCorreo("ana.lopez@example.com");

        Empleado empleado2 = new Empleado();
        empleado2.setId(1);
        empleado2.setRfc("ABCD123456XYZ");
        empleado2.setNombres("Ana");
        empleado2.setApellidos("López Flores");
        empleado2.setCorreo("ana.lopez@example.com");

        Empleado empleado3 = new Empleado();
        empleado3.setId(2);
        empleado3.setRfc("WXYZ987654ABC");
        empleado3.setNombres("Carlos");
        empleado3.setApellidos("Hernández Silva");
        empleado3.setCorreo("carlos.hernandez@example.com");

        // Assert
        assertAll(
                () -> assertEquals(empleado1.getId(), empleado2.getId()),
                () -> assertEquals(empleado1.getNombres(), empleado2.getNombres()),
                () -> assertEquals(empleado1.getApellidos(), empleado2.getApellidos()),
                () -> assertEquals(empleado1.getCorreo(), empleado2.getCorreo())
        );
        assertAll(
                () -> assertNotEquals(empleado1.getId(), empleado3.getId()),
                () -> assertNotEquals(empleado1.getNombres(), empleado3.getNombres()),
                () -> assertNotEquals(empleado1.getApellidos(), empleado3.getApellidos()),
                () -> assertNotEquals(empleado1.getCorreo(), empleado3.getCorreo())
        );
    }
}

