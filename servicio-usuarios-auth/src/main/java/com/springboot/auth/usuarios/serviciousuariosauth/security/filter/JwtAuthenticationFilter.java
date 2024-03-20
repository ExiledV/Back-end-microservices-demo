package com.springboot.auth.usuarios.serviciousuariosauth.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.auth.usuarios.serviciousuariosauth.usuario.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//IMPORTAMOS LAS CONSTANTES DE LOS JWT PARA FIRMAR
import static com.springboot.auth.usuarios.serviciousuariosauth.security.TokenJwtConfig.*;

//Este es el encargado de INICIAR SESION y generar el token (Este extends lo indica)
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    //El constructor de la clase es necesario, inyecta el manager
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //Este es el metodo que intenta hacer la autentificacion
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
        Usuario usuario = null;
                
        /*
         * El servidor de autenticacion, no mapea automaticamente el json de la peticion
         * a nuestro objeto de tipo usuario, asi que lo tenemos que hacer nosotros así
         */
        try {

            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Generamos el token que intenta autenticar
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());

        //Intento de autenticacion
        return authenticationManager.authenticate(authToken);
    }

    //Si todo sale bien en la request del token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

                //USER DE SPRING SECURITY
                User user = ((User) authResult.getPrincipal());
                Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

                //Los claims son datos, son parte del payload (el subject es un claim, pero como es obligatorio se pone ahi y ya)
                Claims claims = Jwts.claims()
                                    //Pasamos los roles como un JSON
                                    .add("authorities", new ObjectMapper().writeValueAsString(roles))
                                    .add("username", user.getUsername())
                                    .build();
                //Añadimos los roles para pasarlos por el token y poder comprobarlos luego

                //Firmamos el token con el usuario
                String jws = Jwts.builder()
                                .subject(user.getUsername())
                                .claims(claims)
                                //Ponemos la fecha de expiracion en la fecha actual + 2 horas
                                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_2H))
                                //Fecha de creacion del token
                                .issuedAt(new Date())
                                .signWith(SECRET_KEY)
                                .compact();
                
                //Añadimos la cabecera que lo detecta
                response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jws);
                //Podemos tb añadir cosas al body de la respuesta, como info random que nos apetezca
                Map<String, String> body = new HashMap<>();
                body.put("token", jws);
                body.put("username", user.getUsername());
                body.put("message", "¡Tu primer jwt! Enhorabuena tio");

                //Pasamos el body a json y se lo asingamos a la respuesta
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                //Y ajustamos variables de la respuesta
                response.setContentType(CONTENT_TYPE);
                response.setStatus(HttpServletResponse.SC_OK);
    }

    //Esto pasa si el inicio de sesion va mal
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        
        
        Map<String, String> body = new HashMap<>();

        body.put("message", "Error en la autenticación, username o password incorrectos");
        //Pintamos el error
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
    }

    

    
}
