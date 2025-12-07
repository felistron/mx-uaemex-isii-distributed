package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.logic.exception.InvalidCredentialsException;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;
import mx.uaemex.fi.backend.presentation.dto.JwtResponse;
import mx.uaemex.fi.backend.presentation.dto.LoginRequest;
import mx.uaemex.fi.backend.presentation.dto.RegisterRequest;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest) throws InvalidCredentialsException;
    EmpleadoResponse register(RegisterRequest registerRequest);
}
