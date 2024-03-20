package com.usuarios.springbootserviciousuarios.usuario.controller;

import org.springframework.web.bind.annotation.RestController;

import com.usuarios.common.springbootserviciocommonsusuarios.models.Usuario;
import com.usuarios.springbootserviciousuarios.usuario.service.UsuarioService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/usuarios")

public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/list")
    public List<Usuario> findAll() {
        Usuario user = new Usuario();
        user.setUsername("Prueba");

        return Collections.singletonList(user);
    }

    @PostMapping("/create")
    public Usuario create(@RequestBody Usuario usuario) {
        System.out.println("Usuario guardado: " + usuario.getUsername());
        return usuario;
    }

    @GetMapping("/search/findByUsername")
    public Usuario findByUsername(@RequestParam String username){
        return usuarioService.findByUsername(username);
    }
    
}
