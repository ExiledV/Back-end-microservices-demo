package com.springboot.auth.usuarios.serviciousuariosauth.usuario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.auth.usuarios.serviciousuariosauth.role.models.Role;
import com.springboot.auth.usuarios.serviciousuariosauth.role.repository.RoleRepository;
import com.springboot.auth.usuarios.serviciousuariosauth.usuario.models.Usuario;
import com.springboot.auth.usuarios.serviciousuariosauth.usuario.repository.UserRepository;

@Service
public class UserServiceimpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired 
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) userRepository.findAll();
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) throws Exception {

        if(!existByUsername(usuario.getUsername())){
            Optional<Role> optPrimaryRole = roleRepository.findByNombre("ROLE_USER");
            List<Role> roles = new ArrayList<>();

            optPrimaryRole.ifPresent(roles::add);
            
            usuario.setRoles(roles);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            return userRepository.save(usuario);
        } else {
            throw new Exception("El usuario ya existe en base de datos");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }
}
