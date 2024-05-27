package com.usuarios.springbootserviciousuarios.usuario.service;

import java.util.List;

import com.usuarios.springbootserviciousuarios.usuario.model.Usuario;

public interface UsuarioService {
    public Usuario create(Usuario usuario);
    public Usuario findByUsername(String username);
    public List<Usuario> findAll();
}
