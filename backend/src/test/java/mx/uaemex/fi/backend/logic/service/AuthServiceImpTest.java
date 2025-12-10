package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.persistence.model.Acceso;
import mx.uaemex.fi.backend.persistence.repository.AccesoRepository;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;
import mx.uaemex.fi.backend.presentation.dto.JwtResponse;
import mx.uaemex.fi.backend.presentation.dto.LoginRequest;
import mx.uaemex.fi.backend.presentation.dto.RegisterRequest;
import mx.uaemex.fi.backend.logic.exception.InvalidCredentialsException;
import mx.uaemex.fi.backend.persistence.model.Empleado;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static mx.uaemex.fi.backend.util.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para AuthServiceImp
 * Verifica la autenticación y registro de usuarios
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImp - Pruebas de autenticación y registro")
class AuthServiceImpTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private AccesoRepository accesoRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImp authService;

    private Empleado empleadoConAcceso;
    private Empleado empleadoSinAcceso;
    private static final String TEST_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test";
    private static final Long TEST_EXPIRATION = 3600000L;

    @BeforeEach
    void setUp() {
        empleadoConAcceso = TestDataBuilder.crearEmpleado();
        empleadoSinAcceso = TestDataBuilder.crearEmpleado();
    }

    // ==================== PRUEBAS DE LOGIN ====================

    @Test
    @DisplayName("login - Credenciales válidas retorna JwtResponse")
    void login_credencialesValidas_retornaJwtResponse() {
        // Arrange
        LoginRequest request = new LoginRequest(TEST_CORREO, TEST_PASSWORD);
        when(empleadoRepository.findByCorreo(TEST_CORREO)).thenReturn(Optional.of(empleadoConAcceso));
        when(accesoRepository.findByIdEmpleado(empleadoConAcceso.getId())).thenReturn(Optional.of(TestDataBuilder.crearAcceso(empleadoConAcceso)));
        when(passwordEncoder.matches(TEST_PASSWORD, TEST_HASHED_PASSWORD)).thenReturn(true);
        when(jwtService.generateToken(TEST_RFC)).thenReturn(TEST_TOKEN);
        when(jwtService.getExpirationTime()).thenReturn(TEST_EXPIRATION);

        // Act
        JwtResponse response = authService.login(request);

        // Assert
        assertNotNull(response, "La respuesta no debe ser null");
        assertEquals(TEST_TOKEN, response.token(), "El token debe coincidir");
        assertEquals("Bearer", response.type(), "El tipo de token debe ser Bearer");
        assertEquals(TEST_EXPIRATION, response.expiresInMs(), "El tiempo de expiración debe coincidir");

        verify(empleadoRepository).findByCorreo(TEST_CORREO);
        verify(passwordEncoder).matches(TEST_PASSWORD, TEST_HASHED_PASSWORD);
        verify(jwtService).generateToken(TEST_RFC);
    }

    @Test
    @DisplayName("login - Correo inexistente lanza InvalidCredentialsException")
    void login_correoInexistente_lanzaInvalidCredentialsException() {
        // Arrange
        LoginRequest request = new LoginRequest("noexiste@test.com", TEST_PASSWORD);
        when(empleadoRepository.findByCorreo("noexiste@test.com")).thenReturn(Optional.empty());

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> authService.login(request));

        assertEquals("Credenciales incorrectas", exception.getMessage());
        verify(empleadoRepository).findByCorreo("noexiste@test.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    @DisplayName("login - Password incorrecta lanza InvalidCredentialsException")
    void login_passwordIncorrecta_lanzaInvalidCredentialsException() {
        // Arrange
        LoginRequest request = new LoginRequest(TEST_CORREO, "passwordIncorrecta");
        when(empleadoRepository.findByCorreo(TEST_CORREO)).thenReturn(Optional.of(empleadoConAcceso));
        when(accesoRepository.findByIdEmpleado(empleadoConAcceso.getId())).thenReturn(Optional.of(TestDataBuilder.crearAcceso(empleadoConAcceso)));
        when(passwordEncoder.matches("passwordIncorrecta", TEST_HASHED_PASSWORD)).thenReturn(false);

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> authService.login(request));

        assertEquals("Credenciales incorrectas", exception.getMessage());
        verify(empleadoRepository).findByCorreo(TEST_CORREO);
        verify(passwordEncoder).matches("passwordIncorrecta", TEST_HASHED_PASSWORD);
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    @DisplayName("login - Empleado sin acceso lanza InvalidCredentialsException")
    void login_empleadoSinAcceso_lanzaInvalidCredentialsException() {
        // Arrange
        LoginRequest request = new LoginRequest(TEST_CORREO, TEST_PASSWORD);
        when(empleadoRepository.findByCorreo(TEST_CORREO)).thenReturn(Optional.of(empleadoSinAcceso));

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> authService.login(request));

        assertEquals("Credenciales incorrectas", exception.getMessage());
        verify(empleadoRepository).findByCorreo(TEST_CORREO);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    // ==================== PRUEBAS DE REGISTER ====================

    @Test
    @DisplayName("register - Empleado normal crea empleado sin acceso")
    void register_empleadoNormal_creaEmpleadoSinAcceso() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                TEST_RFC, TEST_NOMBRE, TEST_APELLIDOS, TEST_CORREO,
                false, null, null
        );

        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setId(1);
        empleadoGuardado.setRfc(TEST_RFC);
        empleadoGuardado.setNombres(TEST_NOMBRE);
        empleadoGuardado.setApellidos(TEST_APELLIDOS);
        empleadoGuardado.setCorreo(TEST_CORREO);

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);

        // Act
        EmpleadoResponse response = authService.register(request);

        // Assert
        assertNotNull(response);
        verify(empleadoRepository).save(any(Empleado.class));
        verify(accesoRepository, never()).save(any(Acceso.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("register - Administrador crea empleado con acceso")
    void register_administrador_creaEmpleadoConAcceso() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                TEST_RFC, TEST_NOMBRE, TEST_APELLIDOS, TEST_CORREO,
                true, TEST_PASSWORD, TEST_PASSWORD
        );

        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setId(1);
        empleadoGuardado.setRfc(TEST_RFC);
        empleadoGuardado.setNombres(TEST_NOMBRE);
        empleadoGuardado.setApellidos(TEST_APELLIDOS);
        empleadoGuardado.setCorreo(TEST_CORREO);

        Acceso accesoGuardado = new Acceso();
        accesoGuardado.setId(1);
        accesoGuardado.setIdEmpleado(1);
        accesoGuardado.setHashedPassword(TEST_HASHED_PASSWORD);

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);
        when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_HASHED_PASSWORD);
        when(accesoRepository.save(any(Acceso.class))).thenReturn(accesoGuardado);

        // Act
        EmpleadoResponse response = authService.register(request);

        // Assert
        assertNotNull(response);
        verify(empleadoRepository).save(any(Empleado.class));
        verify(passwordEncoder).encode(TEST_PASSWORD);
        verify(accesoRepository).save(any(Acceso.class));

        ArgumentCaptor<Acceso> accesoCaptor = ArgumentCaptor.forClass(Acceso.class);
        verify(accesoRepository).save(accesoCaptor.capture());
        Acceso accesoGuardadoCapturado = accesoCaptor.getValue();
        assertEquals(empleadoGuardado.getId(), accesoGuardadoCapturado.getIdEmpleado());
        assertEquals(TEST_HASHED_PASSWORD, accesoGuardadoCapturado.getHashedPassword());
    }

    @Test
    @DisplayName("register - Password se encripta usando PasswordEncoder")
    void register_passwordSeEncripta_usaPasswordEncoder() {
        // Arrange
        String passwordSinEncriptar = "MiPassword123!";
        String passwordEncriptada = "$2a$10$encryptedPasswordHash";

        RegisterRequest request = new RegisterRequest(
                TEST_RFC, TEST_NOMBRE, TEST_APELLIDOS, TEST_CORREO,
                true, passwordSinEncriptar, passwordSinEncriptar
        );

        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setId(1);
        empleadoGuardado.setRfc(TEST_RFC);
        empleadoGuardado.setNombres(TEST_NOMBRE);
        empleadoGuardado.setApellidos(TEST_APELLIDOS);
        empleadoGuardado.setCorreo(TEST_CORREO);

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);
        when(passwordEncoder.encode(passwordSinEncriptar)).thenReturn(passwordEncriptada);
        when(accesoRepository.save(any(Acceso.class))).thenReturn(new Acceso());

        // Act
        authService.register(request);

        // Assert
        verify(passwordEncoder).encode(passwordSinEncriptar);

        ArgumentCaptor<Acceso> accesoCaptor = ArgumentCaptor.forClass(Acceso.class);
        verify(accesoRepository).save(accesoCaptor.capture());
        Acceso accesoGuardado = accesoCaptor.getValue();
        assertEquals(passwordEncriptada, accesoGuardado.getHashedPassword(),
                "La password guardada debe ser la versión encriptada");
    }

    @Test
    @DisplayName("register - Datos válidos retorna EmpleadoResponse")
    void register_datosValidos_retornaEmpleadoResponse() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                TEST_RFC, TEST_NOMBRE, TEST_APELLIDOS, TEST_CORREO,
                false, null, null
        );

        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setId(1);
        empleadoGuardado.setRfc(TEST_RFC);
        empleadoGuardado.setNombres(TEST_NOMBRE);
        empleadoGuardado.setApellidos(TEST_APELLIDOS);
        empleadoGuardado.setCorreo(TEST_CORREO);

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);

        // Act
        EmpleadoResponse response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals(TEST_RFC, response.rfc());
        assertEquals(TEST_NOMBRE, response.nombre());
        assertEquals(TEST_APELLIDOS, response.apellidos());
        assertEquals(TEST_CORREO, response.correo());
    }

    @Test
    @DisplayName("register - Guarda en repositorio llamando save")
    void register_guardaEnRepositorio_llamaSaveEmpleado() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                TEST_RFC, TEST_NOMBRE, TEST_APELLIDOS, TEST_CORREO,
                false, null, null
        );

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoSinAcceso);

        // Act
        authService.register(request);

        // Assert
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }
}

