package com.example.auth.template.auth.models;


import java.io.Serializable;

public class Role implements Serializable {

    private Long id;

    //EL NOMBRE NO DEBE EMPEZAR POR ROLE_, ES UN ESTANDAR DE SPRING SECURITY Y ES IMPORTANTE
    private String nombre;

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
