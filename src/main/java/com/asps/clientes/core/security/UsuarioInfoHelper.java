package com.asps.clientes.core.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInfoHelper {

    public Long getUsuarioId(){
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var jwt = (Jwt) authentication.getPrincipal();

        return jwt.getClaim("usuario_id");
    }
}