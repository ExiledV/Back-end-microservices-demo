package com.gateway.springbootserviciogateway.filters.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter authenticationFilter;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
            http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange((authorize) -> authorize
                .pathMatchers("/api/auth/**").permitAll() //Permitimos solo el de autenticación
                .pathMatchers(HttpMethod.POST ,"/api/usuarios/usuarios/create").permitAll()
                //.pathMatchers("/api/usuarios/usuarios/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .pathMatchers("/api/productos/productos/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .pathMatchers("/api/items/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyExchange().authenticated()
		);
        return http.build();
    }

}
