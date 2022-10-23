package com.asps.clientes.domain.model;

import com.asps.clientes.domain.group.Groups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "estados")
public class Estado {

    @NotNull(groups = Groups.EstadoId.class)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nome;
}