package com.asps.clientes.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EstadoMixin {
    private Long id;

    private String nome;
}