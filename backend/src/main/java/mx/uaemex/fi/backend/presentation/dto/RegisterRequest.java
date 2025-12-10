package mx.uaemex.fi.backend.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import mx.uaemex.fi.backend.logic.validation.ConditionalPassword;
import mx.uaemex.fi.backend.logic.validation.UniqueEmail;
import mx.uaemex.fi.backend.logic.validation.UniqueRFC;

@ConditionalPassword(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{12,}$")
public record RegisterRequest(
        @NotNull(message = "El RFC es obligatorio")
        @Pattern(
                regexp = "^[A-Z&Ñ]{4}[0-9]{6}[A-Z0-9]{3}$",
                message = "El RFC debe tener el siguiente formato: LLLLAAMMDDXXX; en donde L=(A-Z) letra o caracteres & o Ñ, AA=año, MM=mes, DD=día, XXX=alfanumérico; todo en mayúsculas y sin espacios"
        )
        @UniqueRFC
        String rfc,
        @NotNull(message = "El nombre es obligatorio")
        @Pattern(
                regexp = "^[A-ZÁÉÍÓÚÑ]+( [A-ZÁÉÍÓÚÑ]+)*$",
                message = "El nombre debe contener solo letras mayúsculas, un espacio de separación y sin espacios extras al inicio o final"
        )
        String nombre,
        @NotNull(message = "Los apellidos son obligatorios")
        @Pattern(
                regexp = "^[A-ZÁÉÍÓÚÑ]+( [A-ZÁÉÍÓÚÑ]+)*$",
                message = "Los apellidos deben contener solo letras mayúsculas, un espacio de separación y sin espacios extras al inicio o final"
        )
        String apellidos,
        @NotNull(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        @UniqueEmail
        String correo,
        Boolean esAdministrador,
        String password,
        String confirmPassword
) {
}
