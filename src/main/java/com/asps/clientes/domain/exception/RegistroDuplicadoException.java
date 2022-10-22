package com.asps.clientes.domain.exception;

public class RegistroDuplicadoException extends RuntimeException {

    public RegistroDuplicadoException(String message, Throwable cause) {
        super(message,cause);
    }
}
