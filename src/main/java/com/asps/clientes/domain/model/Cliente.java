package com.asps.clientes.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "clientes")
public class Cliente {

    @JsonIgnore
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nome;

    @NotNull
    @CPF
    @Column(nullable = false)
    private String cpf;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Valid
    @NotNull
    @Embedded
    private Endereco endereco;


    @Getter
    @Setter
    @Embeddable
    private static class Endereco {

        @NotEmpty
        @Column(name = "endereco_cep")
        private String cep;

        @NotEmpty
        @Column(name = "endereco_logradouro")
        private String logradouro;

        @NotEmpty
        @Column(name = "endereco_numero")
        private String numero;

        @NotEmpty
        @Column(name = "endereco_bairro")
        private String bairro;

        @NotEmpty
        @Column(name = "endereco_estado")
        private String estado;
    }
}
