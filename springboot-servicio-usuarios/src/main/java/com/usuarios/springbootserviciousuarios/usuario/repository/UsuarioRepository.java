package com.usuarios.springbootserviciousuarios.usuario.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.usuarios.common.springbootserviciocommonsusuarios.models.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>, CrudRepository<Usuario, Long>{

    public Optional<Usuario> findByUsername(String username);
    
}
