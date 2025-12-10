package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.persistence.model.Empleado;
import mx.uaemex.fi.backend.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para UserDetailsAdapter
 * Verifica la adaptación de Empleado a UserDetails de Spring Security
 */
@DisplayName("UserDetailsAdapter - Adaptador de UserDetails")
class UserDetailsAdapterTest {

    private UserDetailsAdapter adapterAdmin;

    @BeforeEach
    void setUp() {
        Empleado empleadoAdmin = TestDataBuilder.crearEmpleado();
        adapterAdmin = new UserDetailsAdapter(empleadoAdmin, TestDataBuilder.TEST_HASHED_PASSWORD);
    }

    // ==================== PRUEBAS DE AUTHORITIES ====================

    @Test
    @DisplayName("getAuthorities - Empleado con acceso retorna ROLE_USER y ROLE_ADMIN")
    void getAuthorities_empleadoConAcceso_retornaUserYAdmin() {
        // Act
        Collection<? extends GrantedAuthority> authorities = adapterAdmin.getAuthorities();

        // Assert
        assertNotNull(authorities, "Las autoridades no deben ser null");
        assertEquals(2, authorities.size(), "Administrador debe tener 2 roles");
        assertTrue(authorities.stream()
                        .anyMatch(auth -> Objects.equals(auth.getAuthority(), "ROLE_USER")),
                "Debe tener ROLE_USER");
        assertTrue(authorities.stream()
                        .anyMatch(auth -> Objects.equals(auth.getAuthority(), "ROLE_ADMIN")),
                "Debe tener ROLE_ADMIN");
    }

    // ==================== PRUEBAS DE PASSWORD ====================

    @Test
    @DisplayName("getPassword - Empleado con acceso retorna password hasheada")
    void getPassword_empleadoConAcceso_retornaHashedPassword() {
        // Act
        String password = adapterAdmin.getPassword();

        // Assert
        assertNotNull(password, "La contraseña no debe ser null");
        assertEquals(TestDataBuilder.TEST_HASHED_PASSWORD, password,
                "Debe retornar la contraseña hasheada del acceso");
    }

    // ==================== PRUEBAS DE USERNAME ====================

    @Test
    @DisplayName("getUsername - Retorna el RFC del empleado")
    void getUsername_retornaRfc() {
        // Act
        String usernameAdmin = adapterAdmin.getUsername();

        // Assert
        assertEquals(TestDataBuilder.TEST_RFC, usernameAdmin, "Username del admin debe ser el RFC");
    }

    // ==================== PRUEBAS DE ESTADO DE CUENTA ====================

    @Test
    @DisplayName("isAccountNonExpired - Siempre retorna true")
    void isAccountNonExpired_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterAdmin.isAccountNonExpired(),
                "La cuenta del admin nunca expira");
    }

    @Test
    @DisplayName("isAccountNonLocked - Siempre retorna true")
    void isAccountNonLocked_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterAdmin.isAccountNonLocked(),
                "La cuenta del admin nunca está bloqueada");
    }

    @Test
    @DisplayName("isCredentialsNonExpired - Siempre retorna true")
    void isCredentialsNonExpired_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterAdmin.isCredentialsNonExpired(),
                "Las credenciales del admin nunca expiran");
    }

    @Test
    @DisplayName("isEnabled - Siempre retorna true")
    void isEnabled_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterAdmin.isEnabled(),
                "El admin siempre está habilitado");
    }
}

