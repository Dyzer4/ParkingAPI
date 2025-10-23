package com.example.estacionamento.Services;

import com.example.estacionamento.Auth.AuthRequest;
import com.example.estacionamento.Auth.JwtUtil;
import com.example.estacionamento.Entity.Usuario;
import com.example.estacionamento.Repository.UsuarioRepository;
import com.example.estacionamento.Exception.UserAlreadyExistsException;
import com.example.estacionamento.Exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Registro
    public void register(AuthRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Usuário com email " + request.getEmail() + " já existe");
        });

        Usuario user = new Usuario();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getSenha())); // criptografa
        user.setAdmin(false);
        userRepository.save(user);
    }

    // Login
    public String login(AuthRequest request) {
        Usuario u = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(request.getSenha(), u.getSenha())) {
            throw new UserNotFoundException("Usuário ou senha inválidos");
        }

        return jwtUtil.generateToken(u.getEmail());
    }
}
