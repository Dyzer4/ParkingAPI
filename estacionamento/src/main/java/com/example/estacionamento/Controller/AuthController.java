package com.example.estacionamento.Controller;

import com.example.estacionamento.Auth.AuthRequest;
import com.example.estacionamento.Auth.AuthResponse;
import com.example.estacionamento.DTO.ErrorResponse;
import com.example.estacionamento.Exception.UserNotFoundException;
import com.example.estacionamento.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Cadastro
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            String token = authService.login(request);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (UserNotFoundException ex) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Falha na autenticação",
                    ex.getMessage(),
                    "/auth/login"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro interno",
                    ex.getMessage(),
                    "/auth/login"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
