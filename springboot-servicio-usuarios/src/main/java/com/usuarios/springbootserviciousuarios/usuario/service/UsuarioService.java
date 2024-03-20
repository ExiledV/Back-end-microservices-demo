package com.usuarios.springbootserviciousuarios.usuario.service;

import java.util.List;

import com.usuarios.common.springbootserviciocommonsusuarios.models.Usuario;

public interface UsuarioService {
    public List<Usuario> list();
    public Usuario create(Usuario usuario);
    public Usuario findByUsername(String username);
}
