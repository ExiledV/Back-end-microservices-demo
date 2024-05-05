package com.usuarios.springbootserviciousuarios.role.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.usuarios.common.springbootserviciocommonsusuarios.models.Role;

@RepositoryRestResource(path = "roles")
public interface RoleRepository extends CrudRepository<Role, Long>{

    public Optional<Role> findByNombre(String nombre);
}
