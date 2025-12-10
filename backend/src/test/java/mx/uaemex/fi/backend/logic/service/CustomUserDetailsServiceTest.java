package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.persistence.model.Empleado;
import mx.uaemex.fi.backend.persistence.repository.AccesoRepository;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static mx.uaemex.fi.backend.util.TestDataBuilder.TEST_RFC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para CustomUserDetailsService
 * Verifica la carga de usuarios para Spring Security
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CustomUserDetailsService - Servicio de UserDetails")
class CustomUserDetailsServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private AccesoRepository accesoRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = TestDataBuilder.crearEmpleado();
    }

    @Test
    @DisplayName("loadUserByUsername - RFC existente retorna UserDetails")
    void loadUserByUsername_rfcExistente_retornaUserDetails() {
        // Arrange
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(Optional.of(empleado));
        when(accesoRepository.findByIdEmpleado(empleado.getId())).thenReturn(Optional.of(TestDataBuilder.crearAcceso(empleado)));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_RFC);

        // Assert
        assertNotNull(userDetails, "UserDetails no debe ser null");
        assertEquals(TEST_RFC, userDetails.getUsername(), "El username debe ser el RFC");
        assertInstanceOf(UserDetailsAdapter.class, userDetails, "Debe retornar una instancia de UserDetailsAdapter");

        verify(empleadoRepository).findByRfc(TEST_RFC);
    }

    @Test
    @DisplayName("loadUserByUsername - RFC inexistente lanza UsernameNotFoundException")
    void loadUserByUsername_rfcInexistente_lanzaUsernameNotFoundException() {
        // Arrange
        String rfcInexistente = "XXXX999999XXX";
        when(empleadoRepository.findByRfc(rfcInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(rfcInexistente));

        assertTrue(exception.getMessage().contains("Empleado no encontrado"),
                "El mensaje debe indicar que el empleado no fue encontrado");

        verify(empleadoRepository).findByRfc(rfcInexistente);
    }

    @Test
    @DisplayName("loadUserByUsername - RFC existente sin acceso lanza UsernameNotFoundException")
    void loadUserByUsername_rfcExistenteSinAcceso_lanzaUsernameNotFoundException() {
        // Arrange
        var empleado = TestDataBuilder.crearEmpleado();
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(Optional.of(empleado));
        when(accesoRepository.findByIdEmpleado(empleado.getId())).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(TEST_RFC));

        assertTrue(exception.getMessage().contains("Acceso no encontrado"),
                "El mensaje debe indicar que el acceso no fue encontrado");

        verify(empleadoRepository).findByRfc(TEST_RFC);
        verify(accesoRepository).findByIdEmpleado(empleado.getId());
    }

    @Test
    @DisplayName("loadUserByUsername - Retorna UserDetailsAdapter correctamente")
    void loadUserByUsername_retornaUserDetailsAdapter() {
        // Arrange
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(Optional.of(empleado));
        when(accesoRepository.findByIdEmpleado(empleado.getId())).thenReturn(Optional.of(TestDataBuilder.crearAcceso(empleado)));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_RFC);

        // Assert
        assertInstanceOf(UserDetailsAdapter.class, userDetails,
                "Debe retornar un UserDetailsAdapter");

        UserDetailsAdapter adapter = (UserDetailsAdapter) userDetails;
        assertEquals(TEST_RFC, adapter.getUsername());
        assertNotNull(adapter.getAuthorities());

        verify(empleadoRepository).findByRfc(TEST_RFC);
    }
}

