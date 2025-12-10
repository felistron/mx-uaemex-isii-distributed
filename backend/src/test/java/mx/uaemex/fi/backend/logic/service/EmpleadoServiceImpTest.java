package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.persistence.model.Empleado;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;
import mx.uaemex.fi.backend.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static mx.uaemex.fi.backend.util.TestDataBuilder.TEST_RFC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para EmpleadoServiceImp
 * Verifica la búsqueda y obtención de empleados
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("EmpleadoServiceImp - Pruebas de servicio de empleado")
class EmpleadoServiceImpTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImp empleadoService;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = TestDataBuilder.crearEmpleado();
    }

    // ==================== PRUEBAS DE BÚSQUEDA POR RFC ====================

    @Test
    @DisplayName("buscarPorRFC - RFC existente retorna empleado")
    void buscarPorRFC_rfcExistente_retornaEmpleado() {
        // Arrange
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(Optional.of(empleado));

        // Act
        EmpleadoResponse resultado = empleadoService.buscarPorRFC(TEST_RFC);

        // Assert
        assertNotNull(resultado, "El empleado no debe ser null");
        assertEquals(TEST_RFC, resultado.rfc(), "El RFC debe coincidir");
        assertEquals(empleado.getNombres(), resultado.nombre());
        assertEquals(empleado.getApellidos(), resultado.apellidos());
        assertEquals(empleado.getCorreo(), resultado.correo());

        verify(empleadoRepository).findByRfc(TEST_RFC);
    }

    @Test
    @DisplayName("buscarPorRFC - RFC inexistente lanza NotFoundException")
    void buscarPorRFC_rfcInexistente_lanzaNotFoundException() {
        // Arrange
        String rfcInexistente = "XXXX999999XXX";
        when(empleadoRepository.findByRfc(rfcInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> empleadoService.buscarPorRFC(rfcInexistente));

        assertEquals("Empleado no encontrado", exception.getMessage());
        verify(empleadoRepository).findByRfc(anyString());
    }

    // ==================== PRUEBAS DE OBTENER TODOS ====================

    @Test
    @DisplayName("obtenerTodos - Empleados existen retorna lista")
    void obtenerTodos_empleadosExisten_retornaLista() {
        // Arrange
        List<Empleado> empleados = TestDataBuilder.crearListaEmpleados();
        when(empleadoRepository.findAll()).thenReturn(empleados);

        // Act
        List<EmpleadoResponse> resultado = empleadoService.obtenerTodos();

        // Assert
        assertNotNull(resultado, "La lista no debe ser null");
        assertFalse(resultado.isEmpty(), "La lista no debe estar vacía");
        assertEquals(2, resultado.size(), "Debe retornar 2 empleados");

        verify(empleadoRepository).findAll();
    }

    @Test
    @DisplayName("obtenerTodos - Sin empleados retorna lista vacía")
    void obtenerTodos_sinEmpleados_retornaListaVacia() {
        // Arrange
        when(empleadoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<EmpleadoResponse> resultado = empleadoService.obtenerTodos();

        // Assert
        assertNotNull(resultado, "La lista no debe ser null");
        assertTrue(resultado.isEmpty(), "La lista debe estar vacía");

        verify(empleadoRepository).findAll();
    }
}

