package mx.uaemex.fi.backend.presentation.dto;

public record JwtResponse(
    String token,
    String type,
    Long expiresInMs
) {
}
