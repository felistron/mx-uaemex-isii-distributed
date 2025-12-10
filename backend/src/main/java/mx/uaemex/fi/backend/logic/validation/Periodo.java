package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodoValidator.class)
public @interface Periodo {
    String message() default "La fecha de inicio debe ser menor a la fecha de fin";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
