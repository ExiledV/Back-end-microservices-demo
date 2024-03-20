package com.productos.springbootservicioproductos.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.productos.springbootservicioproductos.productos.model.Producto;
import com.productos.springbootservicioproductos.productos.service.ProductoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping("/listar")
    public List<Producto> listar(){
        return this.productoService.findAll();
    }
    
    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id){

        /*
        if(id.equals(10L)){
            throw new IllegalStateException();
        }
        //Resilience por defecto tiene 1 segundo de timeout, cuidado con eso
        if(id.equals(7L)){
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        Producto producto = this.productoService.findById(id);

        return producto;
    }
}

