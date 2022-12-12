package com.asps.clientes.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmpresaMixin {

    @JsonIgnore
    private Long id;

    private String razaoSocial;

    private String nomeFantasia;

    private String cnpj;

    private LocalDate dataCriacao;
}