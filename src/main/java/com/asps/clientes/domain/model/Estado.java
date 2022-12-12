package com.asps.clientes.domain.model;

import com.asps.clientes.domain.group.Groups;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "estados")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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