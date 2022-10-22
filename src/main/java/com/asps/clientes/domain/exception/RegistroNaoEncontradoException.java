package com.asps.clientes.domain.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException(String message) {
        super(message);
        log.error(message);
    }
}
