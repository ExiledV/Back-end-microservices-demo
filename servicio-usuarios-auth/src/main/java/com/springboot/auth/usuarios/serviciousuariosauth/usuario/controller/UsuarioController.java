package com.springboot.auth.usuarios.serviciousuariosauth.usuario.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.auth.usuarios.serviciousuariosauth.usuario.models.Usuario;
import com.springboot.auth.usuarios.serviciousuariosauth.usuario.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<Usuario> list() {
        return userService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) throws Exception{
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(userService.save(usuario));
    }

    @GetMapping("/protegida")
    public List<Usuario> list2() {
        return userService.findAll();
    }
    

}
