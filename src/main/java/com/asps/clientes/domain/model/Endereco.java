package com.asps.clientes.domain.model;

import com.asps.clientes.api.model.EstadoMixin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {

    private String cep;

    private String logradouro;

    private String numero;

    private String bairro;

    @JsonIgnoreProperties(value = "id", allowSetters = true)
    private EstadoMixin estado;
}