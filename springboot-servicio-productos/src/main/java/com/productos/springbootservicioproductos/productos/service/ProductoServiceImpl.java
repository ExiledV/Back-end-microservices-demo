package com.productos.springbootservicioproductos.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productos.springbootservicioproductos.productos.model.Producto;
import com.productos.springbootservicioproductos.productos.repository.ProductoRepository;


@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional()
    public List<Producto> findAll() {
        System.out.println("Llamada recibida en el servidor");
        return (List<Producto>) this.productoRepository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public Producto findById(Long id) {
        return this.productoRepository.findById(id).orElse(null);
    }

}
