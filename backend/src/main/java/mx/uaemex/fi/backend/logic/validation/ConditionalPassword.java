package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalPasswordValidator.class)
public @interface ConditionalPassword {
    String message() default "La contraseña es requerida para administradores";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String regexp();
}
