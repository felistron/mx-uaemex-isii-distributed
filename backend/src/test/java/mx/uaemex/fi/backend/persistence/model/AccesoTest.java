package mx.uaemex.fi.backend.persistence.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Acceso.
 * Verifica el correcto funcionamiento del modelo de dominio Acceso.
 */
@DisplayName("Pruebas unitarias de Acceso")
class AccesoTest {

    @Test
    @DisplayName("Debe crear acceso con setter")
    void debeCrearAccesoConBuilder() {
        // Act
        Acceso acceso = new Acceso();
        acceso.setId(1);
        acceso.setIdEmpleado(1);
        acceso.setHashedPassword("$2a$10$hashedPasswordExample");

        // Assert
        assertNotNull(acceso);
        assertEquals(1, acceso.getId());
        assertEquals(1, acceso.getIdEmpleado());
        assertEquals("$2a$10$hashedPasswordExample", acceso.getHashedPassword());
    }

    @Test
    @DisplayName("Debe crear acceso con constructor vacío")
    void debeCrearAccesoConConstructorVacio() {
        // Arrange & Act
        Acceso acceso = new Acceso();

        // Assert
        assertNotNull(acceso);
        assertNull(acceso.getId());
        assertNull(acceso.getIdEmpleado());
        assertNull(acceso.getHashedPassword());
    }

    @Test
    @DisplayName("Debe verificar equals")
    void debeVerificarEquals() {
        // Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setId(1);

        Empleado empleado2 = new Empleado();
        empleado2.setId(2);

        Acceso acceso1 = new Acceso();
        acceso1.setId(1);
        acceso1.setIdEmpleado(empleado1.getId());
        acceso1.setHashedPassword("password123");

        Acceso acceso2 = new Acceso();
        acceso2.setId(1);
        acceso2.setIdEmpleado(empleado1.getId());
        acceso2.setHashedPassword("password123");

        Acceso acceso3 = new Acceso();
        acceso3.setId(2);
        acceso3.setIdEmpleado(empleado2.getId());
        acceso3.setHashedPassword("password456");

        // Assert
        assertAll(
                () -> assertEquals(acceso1.getId(), acceso2.getId()),
                () -> assertEquals(acceso1.getIdEmpleado(), acceso2.getIdEmpleado()),
                () -> assertEquals(acceso1.getHashedPassword(), acceso2.getHashedPassword())
        );
        assertAll(
                () -> assertNotEquals(acceso1.getId(), acceso3.getId()),
                () -> assertNotEquals(acceso1.getIdEmpleado(), acceso3.getIdEmpleado()),
                () -> assertNotEquals(acceso1.getHashedPassword(), acceso3.getHashedPassword())
        );
    }
}
