package mx.uaemex.fi.backend.logic.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para JwtServiceImp
 * Verifica la generación, validación y extracción de datos de tokens JWT
 */
@DisplayName("JwtServiceImp - Pruebas de servicio JWT")
class JwtServiceImpTest {

    private JwtServiceImp jwtService;
    private static final String TEST_SECRET = "dGVzdFNlY3JldEtleUZvckpXVFRlc3RpbmdQdXJwb3Nlc09ubHlEb05vdFVzZUluUHJvZHVjdGlvbg==";
    private static final Long TEST_EXPIRATION = 3600000L; // 1 hora
    private static final String TEST_RFC = "AAAA012345XXX";

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImp();
        // Inyectar valores de configuración usando reflection
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtService, "expiration", TEST_EXPIRATION);
    }

    @Test
    @DisplayName("generateToken - RFC válido genera token válido")
    void generateToken_rfcValido_generaTokenValido() {
        // Act
        String token = jwtService.generateToken(TEST_RFC);

        // Assert
        assertNotNull(token, "El token no debe ser null");
        assertFalse(token.isEmpty(), "El token no debe estar vacío");
        assertEquals(3, token.split("\\.").length, "El token JWT debe tener 3 partes separadas por punto");
    }

    @Test
    @DisplayName("generateToken - Token contiene RFC como sujeto")
    void generateToken_tokenContieneSujeto_rfcComoSujeto() {
        // Act
        String token = jwtService.generateToken(TEST_RFC);
        String rfcExtraido = jwtService.getRfcFromToken(token);

        // Assert
        assertEquals(TEST_RFC, rfcExtraido, "El RFC extraído del token debe coincidir con el RFC original");
    }

    @Test
    @DisplayName("generateToken - Token contiene fechas issuedAt y expiration")
    void generateToken_tokenContieneFechas_issuedAtYExpiration() {
        // Act
        String token = jwtService.generateToken(TEST_RFC);

        // Assert
        assertNotNull(token, "El token no debe ser null");

        // Extraer claims del token
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(TEST_SECRET));
        var claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertNotNull(claims.getIssuedAt(), "El token debe tener fecha de emisión");
        assertNotNull(claims.getExpiration(), "El token debe tener fecha de expiración");

        long tiempoEmision = claims.getIssuedAt().getTime();
        long tiempoExpiracion = claims.getExpiration().getTime();

        // Verificar que la diferencia es aproximadamente 1 hora (puede haber pequeñas diferencias por el tiempo de ejecución)
        long diferencia = tiempoExpiracion - tiempoEmision;
        assertTrue(diferencia >= TEST_EXPIRATION - 1000 && diferencia <= TEST_EXPIRATION + 1000,
                    "La diferencia entre expiración y emisión debe ser aproximadamente " + TEST_EXPIRATION + " ms");
    }

    @Test
    @DisplayName("validateToken - Token válido retorna true")
    void validateToken_tokenValido_retornaTrue() {
        // Arrange
        String token = jwtService.generateToken(TEST_RFC);

        // Act
        boolean esValido = jwtService.validateToken(token);

        // Assert
        assertTrue(esValido, "Un token recién generado debe ser válido");
    }

    @Test
    @DisplayName("validateToken - Token expirado retorna false")
    void validateToken_tokenExpirado_retornaFalse() {
        // Arrange - Crear token con expiración en el pasado
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(TEST_SECRET));
        long ahora = System.currentTimeMillis();
        String tokenExpirado = Jwts.builder()
                .subject(TEST_RFC)
                .issuedAt(new Date(ahora - 7200000)) // Emitido hace 2 horas
                .expiration(new Date(ahora - 3600000)) // Expirado hace 1 hora
                .signWith(key)
                .compact();

        // Act
        boolean esValido = jwtService.validateToken(tokenExpirado);

        // Assert
        assertFalse(esValido, "Un token expirado debe ser inválido");
    }

    @Test
    @DisplayName("validateToken - Token malformado retorna false")
    void validateToken_tokenMalformado_retornaFalse() {
        // Arrange
        String tokenMalformado = "esto.no.es.un.token.valido";

        // Act
        boolean esValido = jwtService.validateToken(tokenMalformado);

        // Assert
        assertFalse(esValido, "Un token malformado debe ser inválido");
    }

    @Test
    @DisplayName("validateToken - Token con firma inválida retorna false")
    void validateToken_tokenInvalido_retornaFalse() {
        // Arrange - Crear token con una clave diferente
        String otraSecret = "b3RyYUNsYXZlU2VjcmV0YVBhcmFHZW5lcmFyVW5Ub2tlbkRpZmVyZW50ZQ==";
        SecretKey otraKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(otraSecret));
        String tokenConOtraFirma = Jwts.builder()
                .subject(TEST_RFC)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TEST_EXPIRATION))
                .signWith(otraKey)
                .compact();

        // Act
        boolean esValido = jwtService.validateToken(tokenConOtraFirma);

        // Assert
        assertFalse(esValido, "Un token con firma inválida debe ser rechazado");
    }

    @Test
    @DisplayName("getRfcFromToken - Token válido retorna RFC")
    void getRfcFromToken_tokenValido_retornaRfc() {
        // Arrange
        String token = jwtService.generateToken(TEST_RFC);

        // Act
        String rfcExtraido = jwtService.getRfcFromToken(token);

        // Assert
        assertEquals(TEST_RFC, rfcExtraido, "El RFC extraído debe coincidir con el RFC original");
    }

    @Test
    @DisplayName("getRfcFromToken - Token inválido lanza excepción")
    void getRfcFromToken_tokenInvalido_lanzaExcepcion() {
        // Arrange
        String tokenInvalido = "token.invalido.aqui";

        // Act & Assert
        assertThrows(Exception.class, () -> jwtService.getRfcFromToken(tokenInvalido), "Extraer RFC de un token inválido debe lanzar excepción");
    }

    @Test
    @DisplayName("getExpirationTime - Retorna valor de configuración")
    void getExpirationTime_retornaValorConfiguracion() {
        // Act
        Long expiration = jwtService.getExpirationTime();

        // Assert
        assertEquals(TEST_EXPIRATION, expiration,
                    "El tiempo de expiración debe coincidir con el valor configurado");
    }

    @Test
    @DisplayName("getSecretKey - Genera clave desde BASE64")
    void getSecretKey_generaClaveDesdeBASE64() {
        // Arrange
        String token = jwtService.generateToken(TEST_RFC);

        // Act - Si la clave no fuera correcta, validateToken fallaría
        boolean esValido = jwtService.validateToken(token);

        // Assert
        assertTrue(esValido, "El token debe ser válido, lo que confirma que la clave secreta se generó correctamente");
    }
}

