package com.springboot.auth.usuarios.serviciousuariosauth.usuario.service;

import java.util.List;

import com.springboot.auth.usuarios.serviciousuariosauth.usuario.models.Usuario;

public interface UserService {

    List<Usuario> findAll();

    Usuario save(Usuario usuario) throws Exception;

    boolean existByUsername(String username);
}
