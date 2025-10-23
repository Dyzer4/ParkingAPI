package com.example.estacionamento.Auth;

import com.example.estacionamento.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrai o header Authorization
        final String authHeader = request.getHeader("Authorization");

        // Variáveis finais para usar dentro da lambda
        final String token;
        final String userEmail;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userEmail = jwtUtil.extractUsername(token);
        } else {
            token = null;
            userEmail = null;
        }

        // Se houver usuário e ainda não estiver autenticado no contexto
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userRepository.findByEmail(userEmail).ifPresent(u -> {
                if (jwtUtil.validateToken(token, u.getEmail())) {
                    User springUser = new User(u.getEmail(), u.getSenha(), new ArrayList<>());
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(springUser, null, springUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            });
        }

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}
