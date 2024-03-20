package com.springboot.auth.usuarios.serviciousuariosauth.security.filter;

import static com.springboot.auth.usuarios.serviciousuariosauth.security.TokenJwtConfig.CONTENT_TYPE;
import static com.springboot.auth.usuarios.serviciousuariosauth.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.springboot.auth.usuarios.serviciousuariosauth.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.springboot.auth.usuarios.serviciousuariosauth.security.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.auth.usuarios.serviciousuariosauth.security.SimpleGrantedAuthoritiesJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if(header == null || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");

        try {
            //Traemos los datos del token
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            //Si seguimos en el try, significa que el token es válido

            //Los sacamos del claims
            String username = claims.getSubject();
            //String username2 = (String) claims.get("username");
            Object authClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                //Le decimos que use la clase abstracta personalizada para que recoja los roles por "authorities" en lugar de por "roles"
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesJsonCreator.class)
                .readValue(authClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            //Creamos el token con todo
            UsernamePasswordAuthenticationToken autenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

            //Comprobamos si puede o no puede
            SecurityContextHolder.getContext().setAuthentication(autenticationToken);

            //Continua al siguiente filtro
            chain.doFilter(request, response);

        } catch (JwtException e) {

            //don't trust the JWT!
            Map<String, String> body = new HashMap<>();

            body.put("error", e.getMessage());
            body.put("message", "El token Jwt es inválido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(CONTENT_TYPE);
        }


    }

    

}
