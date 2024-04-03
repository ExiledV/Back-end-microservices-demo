package com.usuarios.common.springbootserviciocommonsusuarios.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //EL NOMBRE DEBE EMPEZAR POR ROLE_, ES UN ESTANDAR DE SPRING SECURITY Y ES IMPORTANTE
    @Column(unique = true)
    private String nombre;

    //@ManyToMany(mappedBy = "roles")
    //private List<Usuario> usuarios;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
