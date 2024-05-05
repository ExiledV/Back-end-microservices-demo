package com.gateway.springbootserviciogateway.filters.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager{

    @Value("${app.secret.key.jwt}")
    private String SECRET_KEY;
    
    Logger logger = LoggerFactory.getLogger(AuthenticationManagerJwt.class);

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        return Mono.just(authentication.getCredentials().toString())
                .map(token -> {
                    // Asegúrate de usar la misma clave y firma utilizadas en el auth Service
                    SecretKey key = generateKey();
                    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
                })
                .map(claims -> {
                    String username = claims.getSubject();

                    @SuppressWarnings("unchecked")
                    ArrayList<LinkedHashMap<String, String>> roles = claims.get("roles", ArrayList.class); //Devuelve un arraylist de hashmaps

                    List<GrantedAuthority> authorities = new ArrayList<>();

                    //Cada hashmap tiene un elemento "authority" con un rol
                    for(LinkedHashMap<String, String> role : roles) 
                        authorities.add(new SimpleGrantedAuthority(role.get("authority")));

                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                });
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }
}
