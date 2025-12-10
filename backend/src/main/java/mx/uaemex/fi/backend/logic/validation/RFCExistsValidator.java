package mx.uaemex.fi.backend.logic.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RFCExistsValidator implements ConstraintValidator<RFCExists, String> {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public boolean isValid(String rfc, ConstraintValidatorContext constraintValidatorContext) {
        if (rfc == null || rfc.isBlank()) return true;
        return empleadoRepository.existsByRfc(rfc);
    }
}
