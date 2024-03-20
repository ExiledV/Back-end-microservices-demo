package com.usuarios.springbootserviciousuarios.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuarios.common.springbootserviciocommonsusuarios.models.Usuario;
import com.usuarios.springbootserviciousuarios.usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> list() {
        return (List<Usuario>) this.usuarioRepository.findAll();
    }

    @Override
    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findByUsername(String username) {
        return this.usuarioRepository.findByUsername(username).orElse(null);
    }

}
