package com.oauth.springbootserviciooauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oauth.springbootserviciooauth.feignclients.UsuarioFeignClient;
import com.usuarios.common.springbootserviciocommonsusuarios.models.Usuario;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    UsuarioFeignClient usuarioClient;

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioClient.findByUsername(username);

        if(usuario == null){
            logger.info("Error en el login, no existe el usuario " + username + " en el sistema");
            throw new UsernameNotFoundException("Error en el login, no existe el usuario " + username + " en el sistema");
        }

        List<GrantedAuthority> authorities = usuario.getRoles()
                                            .stream()
                                            .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                                            .peek(rol -> logger.info("ROLE: " + rol))
                                            .collect(Collectors.toList());
        
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }

}
