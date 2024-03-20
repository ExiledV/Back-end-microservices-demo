package com.springboot.auth.usuarios.serviciousuariosauth.security;

import java.security.Key;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJwtConfig {

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final int EXPIRATION_TIME_2H = 3600000;
    public static final String CONTENT_TYPE = "application/json";
}
