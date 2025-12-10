package mx.uaemex.fi.backend.persistence.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Nomina.
 * Verifica el correcto funcionamiento del modelo de dominio Nomina.
 */
@DisplayName("Pruebas unitarias de Nomina")
class NominaTest {

    @Test
    @DisplayName("Debe crear nómina con setters")
    void debeCrearNominaConSetters() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = LocalDate.of(2025, 1, 15);
        Empleado empleado = new Empleado();
        empleado.setId(1);
        empleado.setRfc("ABCD123456XYZ");
        empleado.setNombres("Juan");
        empleado.setApellidos("Pérez García");
        empleado.setCorreo("juan.perez@example.com");

        // Act
        Nomina nomina = new Nomina();
        nomina.setId(1);
        nomina.setSalarioBruto(10000d);
        nomina.setExcedente(1500d);
        nomina.setCuotaFija(500d);
        nomina.setPorcentaje(10.88d);
        nomina.setPeriodoInicio(inicio);
        nomina.setPeriodoFin(fin);
        nomina.setEmpleado(empleado);

        // Assert
        assertNotNull(nomina);
        assertEquals(1, nomina.getId());
        assertEquals(10000d, nomina.getSalarioBruto());
        assertEquals(1500d, nomina.getExcedente());
        assertEquals(500d, nomina.getCuotaFija());
        assertEquals(10.88d, nomina.getPorcentaje());
        assertEquals(inicio, nomina.getPeriodoInicio());
        assertEquals(fin, nomina.getPeriodoFin());
        assertEquals(empleado, nomina.getEmpleado());
    }

    @Test
    @DisplayName("Debe crear nómina con constructor vacío")
    void debeCrearNominaConConstructorVacio() {
        // Arrange & Act
        Nomina nomina = new Nomina();

        // Assert
        assertNotNull(nomina);
        assertNull(nomina.getId());
        assertNull(nomina.getSalarioBruto());
        assertNull(nomina.getExcedente());
        assertNull(nomina.getCuotaFija());
        assertNull(nomina.getPorcentaje());
        assertNull(nomina.getPeriodoInicio());
        assertNull(nomina.getPeriodoFin());
        assertNull(nomina.getEmpleado());
    }

    @Test
    @DisplayName("Debe verificar equals")
    void debeVerificarEquals() {
        // Arrange
        LocalDate inicio1 = LocalDate.of(2025, 3, 1);
        LocalDate fin1 = LocalDate.of(2025, 3, 15);
        LocalDate inicio2 = LocalDate.of(2024, 3, 1);
        LocalDate fin2 = LocalDate.of(2024, 3, 15);
        Empleado empleado1 = new Empleado();
        empleado1.setId(1);

        Empleado empleado2 = new Empleado();
        empleado2.setId(2);

        Nomina nomina1 = new Nomina();
        nomina1.setId(1);
        nomina1.setSalarioBruto(10000d);
        nomina1.setExcedente(1500d);
        nomina1.setCuotaFija(500d);
        nomina1.setPorcentaje(10.88d);
        nomina1.setPeriodoInicio(inicio1);
        nomina1.setPeriodoFin(fin1);
        nomina1.setEmpleado(empleado1);

        Nomina nomina2 = new Nomina();
        nomina2.setId(1);
        nomina2.setSalarioBruto(10000d);
        nomina2.setExcedente(1500d);
        nomina2.setCuotaFija(500d);
        nomina2.setPorcentaje(10.88d);
        nomina2.setPeriodoInicio(inicio1);
        nomina2.setPeriodoFin(fin1);
        nomina2.setEmpleado(empleado1);

        Nomina nomina3 = new Nomina();
        nomina3.setId(2);
        nomina3.setSalarioBruto(20000d);
        nomina3.setExcedente(3000d);
        nomina3.setCuotaFija(1000d);
        nomina3.setPorcentaje(16.00d);
        nomina3.setPeriodoInicio(inicio2);
        nomina3.setPeriodoFin(fin2);
        nomina3.setEmpleado(empleado2);

        // Assert
        assertAll(
                () -> assertEquals(nomina1.getId(), nomina2.getId()),
                () -> assertEquals(nomina1.getSalarioBruto(), nomina2.getSalarioBruto()),
                () -> assertEquals(nomina1.getExcedente(), nomina2.getExcedente()),
                () -> assertEquals(nomina1.getCuotaFija(), nomina2.getCuotaFija()),
                () -> assertEquals(nomina1.getPorcentaje(), nomina2.getPorcentaje()),
                () -> assertEquals(nomina1.getPeriodoInicio(), nomina2.getPeriodoInicio()),
                () -> assertEquals(nomina1.getPeriodoFin(), nomina2.getPeriodoFin()),
                () -> assertEquals(nomina1.getEmpleado().getId(), nomina2.getEmpleado().getId())
        );
        assertAll(
                () -> assertNotEquals(nomina1.getId(), nomina3.getId()),
                () -> assertNotEquals(nomina1.getSalarioBruto(), nomina3.getSalarioBruto()),
                () -> assertNotEquals(nomina1.getExcedente(), nomina3.getExcedente()),
                () -> assertNotEquals(nomina1.getCuotaFija(), nomina3.getCuotaFija()),
                () -> assertNotEquals(nomina1.getPorcentaje(), nomina3.getPorcentaje()),
                () -> assertNotEquals(nomina1.getPeriodoInicio(), nomina3.getPeriodoInicio()),
                () -> assertNotEquals(nomina1.getPeriodoFin(), nomina3.getPeriodoFin()),
                () -> assertNotEquals(nomina1.getEmpleado().getId(), nomina3.getEmpleado().getId())
        );
    }
}
