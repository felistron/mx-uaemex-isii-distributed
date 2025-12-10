package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.ConstraintValidatorContext;
import mx.uaemex.fi.backend.presentation.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para ConditionalPasswordValidator
 * Verifica la validación condicional de contraseñas para administradores
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ConditionalPasswordValidator - Validación de contraseñas de administradores")
@MockitoSettings(strictness = Strictness.LENIENT)
class ConditionalPasswordValidatorTest {

    private ConditionalPasswordValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder;

    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{12,}$";
    private static final String PASSWORD_VALIDA = "Qwertyuiop1*";
    private static final String RFC = "AAAA012345XXX";
    private static final String NOMBRE = "JUAN";
    private static final String APELLIDOS = "PEREZ";
    private static final String CORREO = "juan@test.com";

    @BeforeEach
    void setUp() {
        validator = new ConditionalPasswordValidator();

        // Simular la anotación @ConditionalPassword
        ConditionalPassword annotation = mock(ConditionalPassword.class);
        when(annotation.regexp()).thenReturn(REGEX_PASSWORD);
        validator.initialize(annotation);

        // Configurar el mock del context
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
        when(nodeBuilder.addConstraintViolation()).thenReturn(context);
    }

    @Test
    @DisplayName("isValid - No es administrador retorna true")
    void isValid_noEsAdministrador_retornaTrue() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                false, null, null
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertTrue(resultado, "Si no es administrador, debe ser válido sin importar la contraseña");
        verify(context, never()).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    @DisplayName("isValid - Admin con password válida retorna true")
    void isValid_adminConPasswordValida_retornaTrue() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, PASSWORD_VALIDA, PASSWORD_VALIDA
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertTrue(resultado, "Contraseña válida debe retornar true");
    }

    @Test
    @DisplayName("isValid - Admin con password corta retorna false")
    void isValid_adminConPasswordCorta_retornaFalse() {
        // Arrange
        String passwordCorta = "Short1*"; // Menos de 12 caracteres
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, passwordCorta, passwordCorta
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Contraseña menor a 12 caracteres debe ser inválida");
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(contains("12 caracteres"));
    }

    @Test
    @DisplayName("isValid - Admin sin mayúscula retorna false")
    void isValid_adminSinMayuscula_retornaFalse() {
        // Arrange
        String passwordSinMayuscula = "qwertyuiop1*"; // Sin mayúscula
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, passwordSinMayuscula, passwordSinMayuscula
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Contraseña sin mayúscula debe ser inválida");
        verify(context).buildConstraintViolationWithTemplate(contains("mayúscula"));
    }

    @Test
    @DisplayName("isValid - Admin sin minúscula retorna false")
    void isValid_adminSinMinuscula_retornaFalse() {
        // Arrange
        String passwordSinMinuscula = "QWERTYUIOP1*"; // Sin minúscula
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, passwordSinMinuscula, passwordSinMinuscula
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Contraseña sin minúscula debe ser inválida");
        verify(context).buildConstraintViolationWithTemplate(contains("minúscula"));
    }

    @Test
    @DisplayName("isValid - Admin sin número retorna false")
    void isValid_adminSinNumero_retornaFalse() {
        // Arrange
        String passwordSinNumero = "Qwertyuiopas*"; // Sin número
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, passwordSinNumero, passwordSinNumero
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Contraseña sin número debe ser inválida");
        verify(context).buildConstraintViolationWithTemplate(contains("número"));
    }

    @Test
    @DisplayName("isValid - Admin sin carácter especial retorna false")
    void isValid_adminSinCaracterEspecial_retornaFalse() {
        // Arrange
        String passwordSinEspecial = "Qwertyuiop12"; // Sin carácter especial
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, passwordSinEspecial, passwordSinEspecial
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Contraseña sin carácter especial debe ser inválida");
        verify(context).buildConstraintViolationWithTemplate(contains("carácter especial"));
    }

    @Test
    @DisplayName("isValid - Admin passwords no coinciden retorna false")
    void isValid_adminPasswordNoCoinciden_retornaFalse() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, PASSWORD_VALIDA, "DiferentePassword1*"
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Contraseñas que no coinciden deben ser inválidas");
        verify(context).buildConstraintViolationWithTemplate(contains("no coinciden"));
    }

    @Test
    @DisplayName("isValid - Admin password null retorna false")
    void isValid_adminPasswordNull_retornaFalse() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, null, PASSWORD_VALIDA
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Password null para admin debe ser inválido");
        verify(context).buildConstraintViolationWithTemplate(contains("obligatoria"));
    }

    @Test
    @DisplayName("isValid - Admin confirmPassword null retorna false")
    void isValid_adminConfirmPasswordNull_retornaFalse() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, PASSWORD_VALIDA, null
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "ConfirmPassword null para admin debe ser inválido");
        verify(context).buildConstraintViolationWithTemplate(contains("confirmación"));
    }

    @Test
    @DisplayName("isValid - Admin password en blanco retorna false")
    void isValid_adminPasswordBlanco_retornaFalse() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, "   ", PASSWORD_VALIDA
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Password en blanco para admin debe ser inválido");
        verify(context).buildConstraintViolationWithTemplate(contains("obligatoria"));
    }

    @Test
    @DisplayName("isValid - Admin confirmPassword en blanco retorna false")
    void isValid_adminConfirmPasswordBlanco_retornaFalse() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                RFC, NOMBRE, APELLIDOS, CORREO,
                true, PASSWORD_VALIDA, "   "
        );

        // Act
        boolean resultado = validator.isValid(request, context);

        // Assert
        assertFalse(resultado, "Confirm password en blanco para admin debe ser inválido");
        verify(context).buildConstraintViolationWithTemplate(contains("obligatoria"));
    }
}

