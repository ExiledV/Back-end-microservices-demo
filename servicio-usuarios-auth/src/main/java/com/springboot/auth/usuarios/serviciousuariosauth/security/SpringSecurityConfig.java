package com.springboot.auth.usuarios.serviciousuariosauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.springboot.auth.usuarios.serviciousuariosauth.security.filter.JwtAuthenticationFilter;
import com.springboot.auth.usuarios.serviciousuariosauth.security.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/usuarios").permitAll()
            .requestMatchers(HttpMethod.POST, "/usuarios/register").permitAll()
            .anyRequest().authenticated())
            //AQUÍ AÑADIMOS LA CONFIGURACION DEL JWT QUE HEMOS CREADO EN EL PAQUETE FILTER
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtValidationFilter(authenticationManager()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();

            /**
             * Una vez añadido el filtro, se nos crea un Endpoint especial, el cual sería el siguiente:
             * -----> POST ---> localhost:8080/login
             */
    }


}
