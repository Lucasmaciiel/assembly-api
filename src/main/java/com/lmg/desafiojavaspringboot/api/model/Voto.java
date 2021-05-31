package com.lmg.desafiojavaspringboot.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voto")
public class Voto implements Serializable {

    @Id
    @SequenceGenerator(name = "sessao_seq", sequenceName = "sessao_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sessao_seq")
    private Integer id;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @NotNull(message = "A escolha é obrigatório para votar.")
    private Boolean escolha;

    @NotNull(message = "Necessário informar a Pauta para votar.")
    @ManyToOne(fetch = FetchType.EAGER)
    private Pauta pauta;

    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

    @JsonIgnore
    public boolean alreadyExist() {
        return getId() != null;
    }
}