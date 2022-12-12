package com.asps.clientes.domain.model;

import com.asps.clientes.core.validator.ValidacaoNomeEmpresa;
import com.asps.clientes.domain.group.Groups;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ValidacaoNomeEmpresa(groups = Groups.EmpresaDataCriacao.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "empresas")
public class Empresa {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @NotNull
    @CNPJ
    @Column(nullable = false)
    private String cnpj;

    @NotNull(groups = Groups.EmpresaDataCriacao.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;
}