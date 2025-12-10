package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.presentation.dto.NominaRequest;
import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.persistence.model.Empleado;
import mx.uaemex.fi.backend.persistence.model.Nomina;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.persistence.repository.NominaRepository;
import mx.uaemex.fi.backend.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static mx.uaemex.fi.backend.util.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para NominaServiceImp
 * Verifica la generación, eliminación y obtención de nóminas
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("NominaServiceImp - Pruebas de servicio de nómina")
class NominaServiceImpTest {

    @Mock
    private NominaRepository nominaRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private CalculoNominaService calculoNominaService;

    @InjectMocks
    private NominaServiceImp nominaService;

    private Empleado empleado;
    private NominaRequest nominaRequest;
    private Nomina nomina;

    @BeforeEach
    void setUp() {
        empleado = TestDataBuilder.crearEmpleado();
        nominaRequest = new NominaRequest(
                TEST_RFC,
                TEST_SALARIO_RANGO_2,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 15)
        );
        nomina = TestDataBuilder.crearNomina(empleado);
    }

    // ==================== PRUEBAS DE GENERACIÓN DE NÓMINA ====================

    @Test
    @DisplayName("generarNomina - Empleado existente crea nómina")
    void generarNomina_empleadoExistente_creaNomina() {
        // Arrange
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(Optional.of(empleado));
        when(calculoNominaService.calcularExcedente(TEST_SALARIO_RANGO_2)).thenReturn(2253.95);
        when(calculoNominaService.calcularCuotaFija(TEST_SALARIO_RANGO_2)).thenReturn(14.32);
        when(calculoNominaService.calcularPorcentaje(TEST_SALARIO_RANGO_2)).thenReturn(0.0640);
        when(nominaRepository.save(any(Nomina.class))).thenReturn(nomina);

        // Act
        nominaService.generarNomina(nominaRequest);

        // Assert
        verify(empleadoRepository).findByRfc(TEST_RFC);
        verify(calculoNominaService).calcularExcedente(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularCuotaFija(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularPorcentaje(TEST_SALARIO_RANGO_2);
        verify(nominaRepository).save(any(Nomina.class));
    }

    @Test
    @DisplayName("generarNomina - Empleado inexistente lanza NotFoundException")
    void generarNomina_empleadoInexistente_lanzaNotFoundException() {
        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> nominaService.generarNomina(nominaRequest));

        assertEquals("Empleado no encontrado", exception.getMessage());
        verify(empleadoRepository).findByRfc(anyString());
        verify(empleadoRepository, never()).save(any());
    }

    @Test
    @DisplayName("generarNomina - Usa CalculoNominaService correctamente")
    void generarNomina_calculosCorrectos_usaCalculoNominaService() {
        // Arrange
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(Optional.of(empleado));
        when(calculoNominaService.calcularExcedente(TEST_SALARIO_RANGO_2)).thenReturn(2253.95);
        when(calculoNominaService.calcularCuotaFija(TEST_SALARIO_RANGO_2)).thenReturn(14.32);
        when(calculoNominaService.calcularPorcentaje(TEST_SALARIO_RANGO_2)).thenReturn(0.0640);
        when(nominaRepository.save(any(Nomina.class))).thenReturn(nomina);

        // Act
        nominaService.generarNomina(nominaRequest);

        // Assert
        verify(calculoNominaService).calcularExcedente(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularCuotaFija(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularPorcentaje(TEST_SALARIO_RANGO_2);
    }

    // ==================== PRUEBAS DE ELIMINACIÓN DE NÓMINA ====================

    @Test
    @DisplayName("eliminarNomina - ID existente elimina correctamente")
    void eliminarNomina_idExistente_eliminaCorrectamente() {
        // Arrange
        Integer nominaId = 1;
        when(nominaRepository.existsById(nominaId)).thenReturn(true);

        // Act
        nominaService.eliminarNomina(nominaId);

        // Assert
        verify(nominaRepository).existsById(nominaId);
        verify(nominaRepository).deleteById(nominaId);
    }

    @Test
    @DisplayName("eliminarNomina - ID inexistente lanza NotFoundException")
    void eliminarNomina_idInexistente_lanzaNotFoundException() {
        // Arrange
        Integer nominaId = 999;
        when(nominaRepository.existsById(nominaId)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> nominaService.eliminarNomina(nominaId));

        assertEquals("Nómina no encontrada", exception.getMessage());
        verify(nominaRepository).existsById(nominaId);
        verify(nominaRepository, never()).deleteById(anyInt());
    }

    // ==================== PRUEBAS DE CONSULTA DE NÓMINA ====================

    @Test
    @DisplayName("consultarNomina - RFC existente devuelve lista")
    void consultarNomina_rfcExistente_devuelveLista() {
        List<Nomina> nominas = List.of(TestDataBuilder.crearNomina(empleado));

        when(nominaRepository.findAllByEmpleado_RfcOrderByPeriodoInicio(TEST_RFC)).thenReturn(nominas);

        var resultado = nominaService.obtenerTodas(TEST_RFC);

        assertNotNull(resultado, "La lista no debe ser null");
        assertFalse(resultado.isEmpty(), "La lista no debe estar vacía");
        assertEquals(1, resultado.size(), "Debe retornar 1 nomina");

        verify(nominaRepository).findAllByEmpleado_RfcOrderByPeriodoInicio(TEST_RFC);
    }

    @Test
    @DisplayName("consultarNomina - RFC existente devuelve lista")
    void consultarNomina_rfcInexistente_devuelveListaVacia() {
        when(nominaRepository.findAllByEmpleado_RfcOrderByPeriodoInicio(TEST_RFC)).thenReturn(List.of());

        var resultado = nominaService.obtenerTodas(TEST_RFC);

        assertNotNull(resultado, "La lista no debe ser null");
        assertTrue(resultado.isEmpty(), "La lista debe estar vacía");

        verify(nominaRepository).findAllByEmpleado_RfcOrderByPeriodoInicio(TEST_RFC);
    }
}

