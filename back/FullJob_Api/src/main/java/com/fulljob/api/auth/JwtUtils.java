package com.fulljob.api.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

	/**
	 * Esta clase se encarga de crear, leer y validar tokens JWT en la aplicación.
	 *
	 * Lo que hemos hecho aquí es construir un conjunto de utilidades para trabajar con tokens:
	 *
	 * 1. Generar un token:
	 *    - Creamos un token firmado que incluye el nombre del usuario y una fecha de expiración.
	 *    - El token dura 10 horas y se firma con una clave segura.
	 *
	 * 2. Leer datos del token:
	 *    - Podemos extraer el nombre del usuario (username) desde el token.
	 *    - También podemos sacar la fecha de expiración o cualquier otro dato (claim).
	 *
	 * 3. Validar un token:
	 *    - Comprobamos si el nombre del usuario dentro del token coincide con el que esperamos.
	 *    - También revisamos que el token no haya caducado.
	 *
	 * Estas funciones se usan en el filtro de seguridad para saber si un token es válido
	 * y quién es el usuario que lo está usando.
	 */

	
    // Genera una clave segura (256 bits o más) para el algoritmo HS256
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Extrae el username (subject) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrae la fecha de expiración del token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrae un claim del token usando una función
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrae todos los claims del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    // Comprueba si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Genera un token JWT usando los detalles del usuario
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // Crea el token con los claims, subject, fecha de emisión y expiración, y lo firma
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Valida el token comparando el username y comprobando que no esté expirado
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
