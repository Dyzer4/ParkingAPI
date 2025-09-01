package com.example.estacionamento.Auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String nome;
    private String email;
    private String senha;
}
