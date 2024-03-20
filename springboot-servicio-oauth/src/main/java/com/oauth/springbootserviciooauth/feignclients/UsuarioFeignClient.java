package com.oauth.springbootserviciooauth.feignclients;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.usuarios.common.springbootserviciocommonsusuarios.models.Usuario;

@FeignClient(name = "servicio-usuarios")
@LoadBalancerClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {

    @GetMapping("/usuarios/search/findById")
    public Usuario findByUsername(@RequestParam String username);
}
