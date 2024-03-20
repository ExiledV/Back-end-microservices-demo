package com.springboot.auth.usuarios.serviciousuariosauth.role.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springboot.auth.usuarios.serviciousuariosauth.role.models.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

    Optional<Role> findByNombre(String nombre);
}
