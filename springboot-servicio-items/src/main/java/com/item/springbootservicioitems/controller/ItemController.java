package com.item.springbootservicioitems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.item.springbootservicioitems.model.Item;
import com.item.springbootservicioitems.model.Producto;
import com.item.springbootservicioitems.service.ItemService;

@RefreshScope
@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @SuppressWarnings("rawtypes")
    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("serviceFeign")
    private ItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;

    @GetMapping("/listar")
    public List<Item> listar() {
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.cbFactory.create("items")
                    .run(() -> this.itemService.findById(id, cantidad), e -> metodoAlternativo(id, cantidad, e));
        
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {
        Map<String, String> json = new HashMap<>();

        this.logger.info(texto);

        json.put("texto", texto);
        json.put("puerto", puerto);
        return new ResponseEntity<Map<String,String>>(json, HttpStatusCode.valueOf(200));
    }
    
    
    //El método alternativo debería de tener los mismos parámetros
    //que el método de origen
    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e){
        Producto producto = new Producto();

        logger.info(e.getMessage());
        producto.setId(-1L);
        producto.setNombre("Producto ficticio error");
        producto.setPrecio(0.0);

        return new Item(producto, cantidad);
    }
}

