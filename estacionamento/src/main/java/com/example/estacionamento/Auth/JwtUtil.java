package com.example.estacionamento.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    // Inicializa a chave automaticamente quando o componente for criado
    @PostConstruct
    public void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // gera chave segura de 256 bits
    }

    // Gera o token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey) // usa a chave gerada automaticamente
                .compact();
    }

    // Extrai o email (username) do token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Verifica se o token expirou
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Obtém os claims do token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Valida se o token pertence ao email e não expirou
    public boolean validateToken(String token, String email) {
        return extractUsername(token).equals(email) && !isTokenExpired(token);
    }
}
