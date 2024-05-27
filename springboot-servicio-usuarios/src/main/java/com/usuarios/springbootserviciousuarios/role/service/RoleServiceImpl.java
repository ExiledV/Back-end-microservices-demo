package com.usuarios.springbootserviciousuarios.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuarios.springbootserviciousuarios.role.model.Role;
import com.usuarios.springbootserviciousuarios.role.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Role findByNombre(String nombre) {
        return this.roleRepository.findByNombre(nombre).orElse(null);
    }

}
