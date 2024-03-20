package com.gateway.springbootserviciogateway.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion>{

    private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);


    //Constructor que pasa la configuracion a la super clase (Necesario, no se por qué)
    public EjemploGatewayFilterFactory() {
        super(Configuracion.class);
    }

    //Funcion que aplica el filtro, tiene que tener el campo Configuracion creado abajo
    @Override
    public GatewayFilter apply(Configuracion config) {
        return (exchange, chain) -> {
            //Antes de aplicar el filtro
            logger.info("Ejecutando pre gateway filter factory: ");

            //Este es el filtro, cuando termine de construise la request, se pueden realizar acciones con el Mono.fromRunnable()
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                logger.info("Ejecutando post gateway filter factory: " + config.mensaje);

                //Optional de java.util, recordar no usar el de google
                Optional.ofNullable(config.cookieValor).ifPresent(value -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, value).build());
                });
            }));
        };
    }

    //Estas son las variables que se usan en el filtro
    public static class Configuracion {

        private String mensaje;
        private String cookieValor;
        private String cookieNombre;

        public String getMensaje() {
            return mensaje;
        }
        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
        public String getCookieValor() {
            return cookieValor;
        }
        public void setCookieValor(String cookieValor) {
            this.cookieValor = cookieValor;
        }
        public String getCookieNombre() {
            return cookieNombre;
        }
        public void setCookieNombre(String cookieNombre) {
            this.cookieNombre = cookieNombre;
        }


        
    }

}

