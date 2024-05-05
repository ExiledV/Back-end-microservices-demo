package com.usuarios.springbootserviciousuarios.role.service;

import java.util.List;

import com.usuarios.common.springbootserviciocommonsusuarios.models.Role;

public interface RoleService {
    List<Role> findAll();
    Role findByNombre(String nombre);
}
