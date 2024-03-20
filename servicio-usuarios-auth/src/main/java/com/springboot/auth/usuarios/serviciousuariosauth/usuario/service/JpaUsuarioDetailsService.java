package com.springboot.auth.usuarios.serviciousuariosauth.usuario.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.auth.usuarios.serviciousuariosauth.usuario.models.Usuario;
import com.springboot.auth.usuarios.serviciousuariosauth.usuario.repository.UserRepository;

@Service
public class JpaUsuarioDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    //Esto nos permite cargar el usuario por username esté donde esté. (Podemos por BBDD o otro servicio externo)
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> userOptional = repository.findByUsername(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }

        Usuario usuario = userOptional.orElseThrow();

        //Nos traemos los roles y los convertimos a un tipo de Spring Security necesario para devolver UserDetails
        List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());

        //Creamos el usuario de spring security
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true, true, authorities);
    }

}
