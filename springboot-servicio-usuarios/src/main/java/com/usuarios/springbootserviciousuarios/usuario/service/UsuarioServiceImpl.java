package com.usuarios.springbootserviciousuarios.usuario.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.usuarios.common.springbootserviciocommonsusuarios.models.Usuario;
import com.usuarios.springbootserviciousuarios.role.service.RoleService;
import com.usuarios.springbootserviciousuarios.usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) this.usuarioRepository.findAll();
    }

    @Override
    public Usuario create(Usuario usuario) {
        usuario.setRoles(Arrays.asList(this.roleService.findByNombre("USER")));
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findByUsername(String username) {
        return this.usuarioRepository.findByUsername(username).orElse(null);
    }

}
