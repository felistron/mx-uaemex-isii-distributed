package mx.uaemex.fi.backend.presentation.dto;

public record EmpleadoResponse(
        Integer id,
        String rfc,
        String nombre,
        String apellidos,
        String correo
) {
}
