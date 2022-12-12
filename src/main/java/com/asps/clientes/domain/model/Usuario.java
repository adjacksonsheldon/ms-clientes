package com.asps.clientes.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "usuarios")
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String senha;
}