package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mx.uaemex.fi.backend.presentation.dto.RegisterRequest;

public class ConditionalPasswordValidator implements ConstraintValidator<ConditionalPassword, RegisterRequest> {
    private String regexp;

    @Override
    public void initialize(ConditionalPassword conditionalPassword) {
        this.regexp = conditionalPassword.regexp();
    }

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext context) {
        if (Boolean.TRUE.equals(registerRequest.esAdministrador())) {
            var password = registerRequest.password();
            var confirmPassword = registerRequest.confirmPassword();

            context.disableDefaultConstraintViolation();
            var isValid = true;

            if (password == null || password.isBlank()) {
                context.buildConstraintViolationWithTemplate("La contraseña es obligatoria para administradores")
                        .addPropertyNode("password")
                        .addConstraintViolation();
                isValid = false;
            } else {
                if (!password.matches(regexp)) {
                    context.buildConstraintViolationWithTemplate("La contraseña debe tener al menos 12 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial")
                            .addPropertyNode("password")
                            .addConstraintViolation();
                    isValid = false;
                }

                if (confirmPassword == null || confirmPassword.isBlank()) {
                    context.buildConstraintViolationWithTemplate("La confirmación de la contraseña es obligatoria para administradores")
                            .addPropertyNode("confirmPassword")
                            .addConstraintViolation();
                    isValid = false;
                } else if (!password.equals(confirmPassword)) {
                    context.buildConstraintViolationWithTemplate("Las contraseñas no coinciden")
                            .addPropertyNode("confirmPassword")
                            .addConstraintViolation();
                    isValid = false;
                }
            }

            return isValid;
        }
        return true;
    }
}
