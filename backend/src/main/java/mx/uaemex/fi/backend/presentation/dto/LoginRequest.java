package mx.uaemex.fi.backend.presentation.dto;

public record LoginRequest(
        String correo,
        String password
) {
}
