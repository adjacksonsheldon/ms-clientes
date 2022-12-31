package com.asps.clientes.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {
    @PreAuthorize("hasAuthority(cliente_reader) and hasAuthority(SCOPE_READ)")
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface IsAuthenticated {
    }

    @PreAuthorize("hasAuthority(cliente_writer) and hasAuthority(SCOPE_WRITE)")
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface PodeAlterarCliente {
    }
}
