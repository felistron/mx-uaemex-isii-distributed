package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.ConstraintValidatorContext;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para UniqueRFCValidator
 * Verifica que el RFC sea único en el sistema
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UniqueRFCValidator - Validación de RFC único")
class UniqueRFCValidatorTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private UniqueRFCValidator validator;

    @Test
    @DisplayName("isValid - RFC no existe retorna true")
    void isValid_rfcNoExiste_retornaTrue() {
        // Arrange
        String rfc = "AAAA012345XXX";
        when(empleadoRepository.existsByRfc(rfc)).thenReturn(false);

        // Act
        boolean resultado = validator.isValid(rfc, context);

        // Assert
        assertTrue(resultado, "Un RFC que no existe debe ser válido");
        verify(empleadoRepository).existsByRfc(rfc);
    }

    @Test
    @DisplayName("isValid - RFC existe retorna false")
    void isValid_rfcExiste_retornaFalse() {
        // Arrange
        String rfc = "AAAA012345XXX";
        when(empleadoRepository.existsByRfc(rfc)).thenReturn(true);

        // Act
        boolean resultado = validator.isValid(rfc, context);

        // Assert
        assertFalse(resultado, "Un RFC que ya existe debe ser inválido");
        verify(empleadoRepository).existsByRfc(rfc);
    }

    @Test
    @DisplayName("isValid - RFC null retorna true")
    void isValid_rfcNull_retornaTrue() {
        // Act
        boolean resultado = validator.isValid(null, context);

        // Assert
        assertTrue(resultado, "RFC null debe ser válido (validado por @NotNull)");
        verify(empleadoRepository, never()).existsByRfc(anyString());
    }

    @Test
    @DisplayName("isValid - RFC en blanco retorna true")
    void isValid_rfcBlanco_retornaTrue() {
        // Act
        boolean resultado = validator.isValid("   ", context);

        // Assert
        assertTrue(resultado, "RFC en blanco debe ser válido (validado por otras anotaciones)");
        verify(empleadoRepository, never()).existsByRfc(anyString());
    }
}

