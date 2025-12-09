package mx.uaemex.fi.backend.logic.service;

import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.persistence.model.Acceso;
import mx.uaemex.fi.backend.persistence.model.Empleado;
import mx.uaemex.fi.backend.persistence.repository.AccesoRepository;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;
import mx.uaemex.fi.backend.presentation.dto.JwtResponse;
import mx.uaemex.fi.backend.presentation.dto.LoginRequest;
import mx.uaemex.fi.backend.presentation.dto.RegisterRequest;
import mx.uaemex.fi.backend.logic.exception.InvalidCredentialsException;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final EmpleadoRepository empleadoRepository;
    private final AccesoRepository accesoRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(LoginRequest request) throws InvalidCredentialsException {
        var exception = new InvalidCredentialsException("Credenciales incorrectas");

        var empleado = empleadoRepository.findByCorreo(request.correo())
                .orElseThrow(() -> exception);

        var acceso = accesoRepository.findByIdEmpleado(empleado.getId())
                .orElseThrow(() -> exception);

        if (passwordEncoder.matches(request.password(), acceso.getHashedPassword())) {
            var token = jwtService.generateToken(empleado.getRfc());
            return new JwtResponse(token, "Bearer", jwtService.getExpirationTime());
        }

        throw exception;
    }

    @Transactional
    @Override
    public EmpleadoResponse register(RegisterRequest request) {
        var empleado = new Empleado();
        empleado.setRfc(request.rfc());
        empleado.setNombres(request.nombre());
        empleado.setApellidos(request.apellidos());
        empleado.setCorreo(request.correo());

        var res = empleadoRepository.save(empleado);

        if (Boolean.TRUE.equals(request.esAdministrador())) {
            var hashedPassword = passwordEncoder.encode(request.password());

            var acceso = new Acceso();
            acceso.setIdEmpleado(res.getId());
            acceso.setHashedPassword(hashedPassword);

            accesoRepository.save(acceso);
        }

        return new EmpleadoResponse(
                res.getId(),
                res.getRfc(),
                res.getNombres(),
                res.getApellidos(),
                res.getCorreo()
        );
    }
}
