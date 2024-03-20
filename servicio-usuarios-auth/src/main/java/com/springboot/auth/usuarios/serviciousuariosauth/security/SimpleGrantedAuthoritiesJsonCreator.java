package com.springboot.auth.usuarios.serviciousuariosauth.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Esta es una clase que necesitamos en el JwtValidationFilter
 * debido a que los GrantedAuthorities, solo son capaces de parsear
 * los roles que tengan en los Claims el nombre "role", como nosotros
 * queremos que se llamen authorities, tenemos que implementar esta clase
 * que nos ayuda a parsearlos, para poder validar el token.
 */
public abstract class SimpleGrantedAuthoritiesJsonCreator {
    @JsonCreator
    public SimpleGrantedAuthoritiesJsonCreator(@JsonProperty("authority") String role){}
}
