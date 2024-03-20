package com.productos.springbootservicioproductos.productos.repository;

import org.springframework.data.repository.CrudRepository;

import com.productos.springbootservicioproductos.productos.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long>{

}
