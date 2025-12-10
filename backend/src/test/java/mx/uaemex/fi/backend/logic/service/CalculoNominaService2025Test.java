package mx.uaemex.fi.backend.logic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para CalculoNominaService2025
 * Verifica que los cálculos de ISR cumplan con las tablas del SAT 2025
 * Referencia: Anexo 8 RMF 2025
 */
@DisplayName("CalculoNominaService2025 - Pruebas de cálculos fiscales SAT 2025")
class CalculoNominaService2025Test {

    private CalculoNominaService2025 calculoNominaService;

    @BeforeEach
    void setUp() {
        calculoNominaService = new CalculoNominaService2025();
    }

    // ==================== PRUEBAS DE CUOTA FIJA ====================

    @Test
    @DisplayName("calcularCuotaFija - Rango 1 (0.01-746.04) retorna 0.00")
    void calcularCuotaFija_rangoMinimo_retorna0() {
        // Arrange
        Double salarioMinimo = 500.00;
        Double salarioLimite = 746.04;

        // Act
        Double cuotaMinimo = calculoNominaService.calcularCuotaFija(salarioMinimo);
        Double cuotaLimite = calculoNominaService.calcularCuotaFija(salarioLimite);

        // Assert
        assertEquals(0.00f, cuotaMinimo, 0.01f, "Salario 500.00 debe tener cuota fija 0.00");
        assertEquals(0.00f, cuotaLimite, 0.01f, "Salario 746.04 debe tener cuota fija 0.00");
    }

    @ParameterizedTest
    @CsvSource({
            "746.04",
            "6332.06",
            "-10",
    })
    void calcularCuotaFija_rango2_casosFueraDelRango(Double salario) {
        Double cuota = calculoNominaService.calcularExcedente(salario);
        assertNotEquals(14.32, cuota); // No debe entrar al rango 2
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 2 (746.05-6332.05) retorna 14.32")
    void calcularCuotaFija_rango2_retorna14_32() {
        // Arrange
        Double salarioMedio = 3000.00;
        Double salarioFin = 6332.05;

        // Act
        Double cuotaMedio = calculoNominaService.calcularCuotaFija(salarioMedio);
        Double cuotaFin = calculoNominaService.calcularCuotaFija(salarioFin);

        // Assert
        assertEquals(14.32f, cuotaMedio, 0.01f, "Salario 3000.00 debe tener cuota fija 14.32");
        assertEquals(14.32f, cuotaFin, 0.01f, "Salario 6332.05 debe tener cuota fija 14.32");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 3 (6332.06-11128.01) retorna 371.83")
    void calcularCuotaFija_rango3_retorna371_83() {
        // Arrange
        Double salario = 8000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(371.83f, cuota, 0.01f, "Salario 8000.00 debe tener cuota fija 371.83");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 4 (11128.02-12935.82) retorna 893.63")
    void calcularCuotaFija_rango4_retorna893_63() {
        // Arrange
        Double salario = 12000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(893.63f, cuota, 0.01f, "Salario 12000.00 debe tener cuota fija 893.63");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 5 (12935.83-15487.71) retorna 1182.88")
    void calcularCuotaFija_rango5_retorna1182_88() {
        // Arrange
        Double salario = 14000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(1182.88f, cuota, 0.01f, "Salario 14000.00 debe tener cuota fija 1182.88");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 6 (15487.72-31236.49) retorna 1640.18")
    void calcularCuotaFija_rango6_retorna1640_18() {
        // Arrange
        Double salario = 20000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(1640.18f, cuota, 0.01f, "Salario 20000.00 debe tener cuota fija 1640.18");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 7 (31236.50-49233.00) retorna 5004.12")
    void calcularCuotaFija_rango7_retorna5004_12() {
        // Arrange
        Double salario = 40000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(5004.12f, cuota, 0.01f, "Salario 40000.00 debe tener cuota fija 5004.12");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 8 (49233.01-93993.90) retorna 9236.89")
    void calcularCuotaFija_rango8_retorna9236_89() {
        // Arrange
        Double salario = 70000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(9236.89f, cuota, 0.01f, "Salario 70000.00 debe tener cuota fija 9236.89");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 9 (93993.91-125325.20) retorna 22665.17")
    void calcularCuotaFija_rango9_retorna22665_17() {
        // Arrange
        Double salario = 100000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(22665.17f, cuota, 0.01f, "Salario 100000.00 debe tener cuota fija 22665.17");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 10 (125325.21-375975.61) retorna 32691.18")
    void calcularCuotaFija_rango10_retorna32691_18() {
        // Arrange
        Double salario = 200000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(32691.18f, cuota, 0.01f, "Salario 200000.00 debe tener cuota fija 32691.18");
    }

    @Test
    @DisplayName("calcularCuotaFija - Rango 11 (>375975.61) retorna 117912.32")
    void calcularCuotaFija_rangoMaximo_retorna117912_32() {
        // Arrange
        Double salario = 500000.00;

        // Act
        Double cuota = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(117912.32f, cuota, 0.01f, "Salario 500000.00 debe tener cuota fija 117912.32");
    }

    @ParameterizedTest
    @CsvSource({
            "500.00, 0.00",
            "746.04, 0.00",
            "3000.00, 14.32",
            "6332.05, 14.32",
            "8000.00, 371.83",
            "11128.01, 371.83",
            "12000.00, 893.63",
            "20000.00, 1640.18",
            "31000.00, 1640.18",
            "40000.00, 5004.12",
            "200000.00, 32691.18",
            "500000.00, 117912.32"
    })
    @DisplayName("calcularCuotaFija - Valores límite de rangos calculan correctamente")
    void calcularCuotaFija_limitesDeRango_calculaCorrectamente(Double salario, Double cuotaEsperada) {
        // Act
        Double cuotaObtenida = calculoNominaService.calcularCuotaFija(salario);

        // Assert
        assertEquals(cuotaEsperada, cuotaObtenida, 0.01f,
                    String.format("Salario %.2f debe tener cuota fija %.2f", salario, cuotaEsperada));
    }

    // ==================== PRUEBAS DE EXCEDENTE ====================

    @Test
    @DisplayName("calcularExcedente - Rango mínimo calcula correctamente")
    void calcularExcedente_rangoMinimo_calculaCorrectamente() {
        // Arrange
        double salario = 500.00;
        double excedenteEsperado = salario - 0.01;

        // Act
        Double excedente = calculoNominaService.calcularExcedente(salario);

        // Assert
        assertEquals(excedenteEsperado, excedente, 0.01f,
                    "Excedente debe ser salario - límite inferior");
    }

    @ParameterizedTest
    @CsvSource({
            "746.04",
            "6332.06",
            "-10",
    })
    void calcularExcedente_rango2_casosFueraDelRango(Double salario) {
        Double cuota = calculoNominaService.calcularCuotaFija(salario);
        assertNotEquals(salario - 746.05, cuota); // No debe entrar al rango 2
    }

    @ParameterizedTest
    @CsvSource({
            "3000.00, 2253.95",  // Rango 2: 3000 - 746.05
            "8000.00, 1667.94",  // Rango 3: 8000 - 6332.06
            "12000.00, 871.98",  // Rango 4: 12000 - 11128.02
            "14000.00, 1064.17", // Rango 5: 14000 - 12935.83
            "20000.00, 4512.28", // Rango 6: 20000 - 15487.72
            "40000.00, 8763.50", // Rango 7: 40000 - 31236.50
            "70000.00, 20766.99",// Rango 8: 70000 - 49233.01
            "100000.00, 6006.09",// Rango 9: 100000 - 93993.91
            "200000.00, 74674.79",// Rango 10: 200000 - 125325.21
            "500000.00, 124024.38" // Rango 11: 500000 - 375975.62
    })
    @DisplayName("calcularExcedente - Todos los rangos calculan correctamente")
    void calcularExcedente_todosLosRangos_calculaCorrectamente(Double salario, Double excedenteSperado) {
        // Act
        Double excedente = calculoNominaService.calcularExcedente(salario);

        // Assert
        assertEquals(excedenteSperado, excedente, 0.01f,
                    String.format("Excedente para salario %.2f debe ser %.2f", salario, excedenteSperado));
    }


    // ==================== PRUEBAS DE PORCENTAJE ====================

    @ParameterizedTest
    @CsvSource({
            "746.04",
            "6332.06",
            "-10",
    })
    void calcularPorcentaje_rango2_casosFueraDelRango(Double salario) {
        Double cuota = calculoNominaService.calcularPorcentaje(salario);
        assertNotEquals(0.0640, cuota); // No debe entrar al rango 2
    }

    @Test
    @DisplayName("calcularPorcentaje - Rango mínimo retorna 1.92%")
    void calcularPorcentaje_rangoMinimo_retorna1_92Porciento() {
        // Arrange
        Double salario = 500.00;

        // Act
        Double porcentaje = calculoNominaService.calcularPorcentaje(salario);

        // Assert
        assertEquals(0.0192f, porcentaje, 0.0001f, "Rango mínimo debe tener porcentaje 1.92%");
    }

    @ParameterizedTest
    @CsvSource({
            "500.00, 0.0192",    // Rango 1: 1.92%
            "3000.00, 0.0640",   // Rango 2: 6.40%
            "8000.00, 0.1088",   // Rango 3: 10.88%
            "12000.00, 0.1600",  // Rango 4: 16.00%
            "14000.00, 0.1792",  // Rango 5: 17.92%
            "20000.00, 0.2136",  // Rango 6: 21.36%
            "40000.00, 0.2352",  // Rango 7: 23.52%
            "70000.00, 0.3000",  // Rango 8: 30.00%
            "100000.00, 0.3200", // Rango 9: 32.00%
            "200000.00, 0.3400", // Rango 10: 34.00%
            "500000.00, 0.3500"  // Rango 11: 35.00%
    })
    @DisplayName("calcularPorcentaje - Todos los rangos retornan porcentaje correcto")
    void calcularPorcentaje_todosLosRangos_retornaPorcentajesCorrecto(Double salario, Double porcentajeEsperado) {
        // Act
        Double porcentaje = calculoNominaService.calcularPorcentaje(salario);

        // Assert
        assertEquals(porcentajeEsperado, porcentaje, 0.0001f,
                    String.format("Salario %.2f debe tener porcentaje %.4f", salario, porcentajeEsperado));
    }

    @Test
    @DisplayName("calcularPorcentaje - Rango máximo retorna 35%")
    void calcularPorcentaje_rangoMaximo_retorna35Porciento() {
        // Arrange
        Double salario = 1000000.00;

        // Act
        Double porcentaje = calculoNominaService.calcularPorcentaje(salario);

        // Assert
        assertEquals(0.3500f, porcentaje, 0.0001f, "Rango máximo debe tener porcentaje 35%");
    }
}

