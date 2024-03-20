package com.productos.springbootservicioproductos.productos.service;

import java.util.List;

import com.productos.springbootservicioproductos.productos.model.Producto;

public interface ProductoService {

    public List<Producto> findAll();
    public Producto findById(Long id);
}
