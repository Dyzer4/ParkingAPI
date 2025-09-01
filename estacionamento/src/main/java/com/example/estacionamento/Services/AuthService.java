// Pacote da classe de serviço (camada de lógica de negócios)
package com.example.estacionamento.Services;

// Importações das classes necessárias
import com.example.estacionamento.Auth.AuthRequest;
import com.example.estacionamento.Auth.JwtUtil;
import com.example.estacionamento.Entity.Usuario;
import com.example.estacionamento.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

// Marca esta classe como um Service (será gerenciada pelo Spring)
@Service

// Lombok: cria um construtor com todos os campos "final" para injeção de dependência
@RequiredArgsConstructor
public class AuthService {

    // Injeção de dependências via construtor (com @RequiredArgsConstructor)
    private final UsuarioRepository userRepository;            // Acesso ao banco de dados
    private final PasswordEncoder passwordEncoder;             // Criptografia da senha
    private final JwtUtil jwtUtil;                             // Classe utilitária para gerar e validar JWT
    private final AuthenticationManager authenticationManager; // Gerenciador de autenticação do Spring

    /**
     * Método responsável por registrar um novo usuário.
     * Verifica se o e-mail já está cadastrado, senão salva o novo usuário no banco.
     */
    public void register(AuthRequest request) {
        // Verifica se o e-mail já está em uso
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Usuário já existe");
        });

        // Cria um novo objeto de usuário com os dados recebidos
        Usuario user = new Usuario();
        user.setNome(request.getNome()); // Nome fixo por enquanto — pode ser ajustado para vir no request
        user.setEmail(request.getEmail());

        // Codifica a senha antes de salvar no banco
        user.setSenha(passwordEncoder.encode(request.getSenha()));

        // Define o usuário como não-admin (por padrão)
        user.setAdmin(false);

        // Salva o novo usuário no banco de dados
        userRepository.save(user);
    }


      //Método responsável por autenticar o usuário e gerar o token JWT.
      //@param request objeto contendo email e senha.
      //@return token JWT se autenticação for bem-sucedida.

    public String login(AuthRequest request) {
        // Autentica o usuário usando o AuthenticationManager (verifica email e senha)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        // Busca o usuário no banco após autenticação
        Usuario u = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria um UserDetails com os dados do usuário (sem roles/authorities)
        UserDetails userDetails = new User(u.getEmail(), u.getSenha(), new ArrayList<>());

        // Gera e retorna o token JWT para esse usuário
        return jwtUtil.generateToken(userDetails);
    }
}
