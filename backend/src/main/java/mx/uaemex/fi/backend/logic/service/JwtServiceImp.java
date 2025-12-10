package mx.uaemex.fi.backend.logic.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImp implements JwtService {
    @Value( "${app.jwt.secret}")
    private String secret;

    @Value( "${app.jwt.expiration}")
    private Long expiration;

    @Override
    public String generateToken(String rfc) {
        var now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(rfc)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return (new Date()).before(claims.getExpiration());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getRfcFromToken(String token) {
        var claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Long getExpirationTime() {
        return expiration;
    }
}
