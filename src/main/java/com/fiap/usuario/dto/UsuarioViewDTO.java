package com.fiap.usuario.dto;

import com.fiap.usuario.model.Usuario;
import com.fiap.usuario.model.UsuarioRole;

public record UsuarioViewDTO(
        Long usuarioId,
        String nome,
        String email,
        UsuarioRole role) {

    public UsuarioViewDTO(Usuario usuario){
        this (
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole());
    }
}
