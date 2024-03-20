package com.springboot.auth.usuarios.serviciousuariosauth.usuario.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springboot.auth.usuarios.serviciousuariosauth.usuario.models.Usuario;

public interface UserRepository extends CrudRepository<Usuario, Long>{

    boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);
    
}
