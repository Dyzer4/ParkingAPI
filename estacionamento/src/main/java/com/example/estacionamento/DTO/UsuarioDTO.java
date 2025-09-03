package com.example.estacionamento.DTO;


import com.example.estacionamento.Entity.Usuario;

public record UsuarioDTO(String nome, String email) {

    // Método de conveniência para converter de Usuario para DTO
    public static UsuarioDTO fromEntity(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(), usuario.getEmail());
    }
}
