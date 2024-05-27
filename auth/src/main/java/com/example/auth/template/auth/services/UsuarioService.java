package com.example.auth.template.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.auth.template.auth.FeignClient.UsuariosFeign;
import com.example.auth.template.auth.models.Role;
import com.example.auth.template.auth.models.Usuario;


public class UsuarioService implements UserDetailsService{

    @Autowired
    UsuariosFeign usuarioFeign;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioFeign.findByUsername(username);

        if(usuario == null){
            throw new UsernameNotFoundException("Error en el login, no existe el usuario " + username + " en el sistema");
        }

        String[] authorities = usuario.getRoles()
                                    .stream()
                                    .map(Role::getNombre)
                                    .toList()
                                    .toArray(String[]::new);
        
        return User.withUsername(usuario.getUsername())
            .password(usuario.getPassword())
            .roles(authorities)
            .disabled(usuario.getEnabled())
            .build();
    }
}
