package com.example.auth.template.auth.FeignClient;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.auth.template.auth.models.Usuario;



@FeignClient(name = "servicio-usuarios")
@LoadBalancerClient(name = "servicio-usuarios")
public interface UsuariosFeign {

    @GetMapping("/usuarios/search/findByUsername")
    public Usuario findByUsername(@RequestParam String username);
}
