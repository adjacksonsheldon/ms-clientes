package com.asps.clientes.api.model;

import com.asps.clientes.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteMixin {

    @JsonIgnore
    private Long id;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private Endereco endereco;
}
