package com.example.estacionamento.Controller;

import com.example.estacionamento.Auth.AuthRequest;
import com.example.estacionamento.Auth.AuthResponse;
import com.example.estacionamento.DTO.UsuarioDTO;
import com.example.estacionamento.Repository.UsuarioRepository;
import com.example.estacionamento.Services.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    private final UsuarioRepository usuarioRepository;
    @GetMapping("/me")
    public ResponseEntity<?> getUsuarioLogado(Authentication authentication) {
        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .map(usuario -> ResponseEntity.ok(UsuarioDTO.fromEntity(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

}





