package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RFCExistsValidator.class)
public @interface RFCExists {
    String message() default "El RFC no existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
