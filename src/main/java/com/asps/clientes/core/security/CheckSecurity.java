package com.asps.clientes.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {
    @PreAuthorize("isAuthenticated()")
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface IsAuthenticated {
    }

    @PreAuthorize("hasAuthority(write)")
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface PodeAlterar {
    }
}
