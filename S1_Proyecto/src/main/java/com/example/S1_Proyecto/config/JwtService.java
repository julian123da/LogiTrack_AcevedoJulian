package com.example.S1_Proyecto.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys; // Importación necesaria
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // La clave debe ser larga para cumplir con la seguridad de HS256
    private final String SECRET = "clave_super_secreta_para_clase_2026_logitrack_proyecto";
    private final long EXPIRATION = 1000 * 60 * 30; // 30 minutos

    private SecretKey getKey() {
        // En 0.12.x se recomienda usar getBytes con StandardCharsets
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Generar token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // Antes era setSubject
                .issuedAt(new Date()) // Antes era setIssuedAt
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION)) // Antes era setExpiration
                .signWith(getKey()) // Ya no necesita SignatureAlgorithm.HS256, lo detecta solo
                .compact();
    }

    // Validar token
    public String validateToken(String token) {
        try {
            return Jwts.parser() // Antes era parserBuilder()
                    .verifyWith(getKey()) // Antes era setSigningKey()
                    .build()
                    .parseSignedClaims(token) // Antes era parseClaimsJws()
                    .getPayload() // Antes era getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}