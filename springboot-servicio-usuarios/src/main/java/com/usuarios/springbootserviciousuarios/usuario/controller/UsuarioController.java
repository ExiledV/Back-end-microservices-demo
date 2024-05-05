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

    @GetMapping("/findAll")
    public List<Usuario> findAll() {
        return this.usuarioService.findAll();
    }

    @PostMapping("/create")
    public Usuario create(@RequestBody Usuario usuario) {
        return this.usuarioService.create(usuario);
    }

    @GetMapping("/search/findByUsername")
    public Usuario findByUsername(@RequestParam String username){
        return usuarioService.findByUsername(username);
    }
    
}
