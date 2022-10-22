package com.asps.clientes.domain.model;

import com.asps.clientes.domain.group.Groups;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "empresas")
public class Empresa {

    @JsonIgnore
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nome;

    @NotNull
    @CNPJ
    @Column(nullable = false)
    private String cnpj;

    @NotNull(groups = Groups.EmpresaCadastro.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;
}