package com.asps.clientes.domain.model;

import com.asps.clientes.domain.group.Groups;
import com.asps.clientes.core.validator.MaiorIdade;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "clientes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

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

    @MaiorIdade
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    @Valid
    @NotNull
    @Embedded
    private Endereco endereco;

    @Setter
    @Column(name = "usuario_cadastro_id")
    private Long usuarioCadastroId;

    @Getter
    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Endereco {

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

        @Setter
        @Valid
        @ConvertGroup(to = Groups.EstadoId.class)
        @NotNull
        @ManyToOne
        @JoinColumn(name = "endereco_estado_id", nullable = false)
        private Estado estado;
    }
}
