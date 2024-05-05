package com.example.auth.template.auth.services.auth;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.auth.template.auth.FeignClient.UsuariosFeign;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {

    @Value("${app.secret.key.jwt}")
    private String SECRET_KEY;

    Logger log = LoggerFactory.getLogger(JwtService.class);

    private final int EXPIRATION_IN_MINUTES = 60;

    @Autowired
    UsuariosFeign usuarioService;

    public String generateToken(UserDetails userDetails){

        // Fecha actual
        Date issuedAt = new Date(System.currentTimeMillis());
        // Se pasa el tiempo a milisegundos y se le suma al inicial
        Date expiration = new Date( (EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .signWith(generateKey(), Jwts.SIG.HS256)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        
        try {
            // Validar la firma del token
            Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            // Firma inválida
            log.error("Firma JWT inválida: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            // Token JWT mal formado
            log.error("Token JWT mal formado: {}", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            // Token JWT expirado
            log.error("Token JWT expirado: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            // Token JWT no compatible
            log.error("Token JWT no compatible: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            // Argumento inválido
            log.error("Argumento inválido al validar token JWT: {}", e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }
}
