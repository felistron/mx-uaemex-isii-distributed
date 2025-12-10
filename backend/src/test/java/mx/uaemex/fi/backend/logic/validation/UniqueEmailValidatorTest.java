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
 * Pruebas unitarias para UniqueEmailValidator
 * Verifica que el correo electrónico sea único en el sistema
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UniqueEmailValidator - Validación de correo único")
class UniqueEmailValidatorTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private UniqueEmailValidator validator;

    @Test
    @DisplayName("isValid - Correo no existe retorna true")
    void isValid_correoNoExiste_retornaTrue() {
        // Arrange
        String correo = "nuevo@test.com";
        when(empleadoRepository.existsByCorreo(correo)).thenReturn(false);

        // Act
        boolean resultado = validator.isValid(correo, context);

        // Assert
        assertTrue(resultado, "Un correo que no existe debe ser válido");
        verify(empleadoRepository).existsByCorreo(correo);
    }

    @Test
    @DisplayName("isValid - Correo existe retorna false")
    void isValid_correoExiste_retornaFalse() {
        // Arrange
        String correo = "existente@test.com";
        when(empleadoRepository.existsByCorreo(correo)).thenReturn(true);

        // Act
        boolean resultado = validator.isValid(correo, context);

        // Assert
        assertFalse(resultado, "Un correo que ya existe debe ser inválido");
        verify(empleadoRepository).existsByCorreo(correo);
    }

    @Test
    @DisplayName("isValid - Correo null retorna true")
    void isValid_correoNull_retornaTrue() {
        // Act
        boolean resultado = validator.isValid(null, context);

        // Assert
        assertTrue(resultado, "Correo null debe ser válido (validado por @NotNull)");
        verify(empleadoRepository, never()).existsByCorreo(anyString());
    }

    @Test
    @DisplayName("isValid - Correo en blanco retorna true")
    void isValid_correoBlanco_retornaTrue() {
        // Act
        boolean resultado = validator.isValid("   ", context);

        // Assert
        assertTrue(resultado, "Correo en blanco debe ser válido (validado por otras anotaciones)");
        verify(empleadoRepository, never()).existsByCorreo(anyString());
    }
}

