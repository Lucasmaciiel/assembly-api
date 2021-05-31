package com.lmg.desafiojavaspringboot.api.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "sessao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Sessao {

    @Id
    @SequenceGenerator(name = "sessao_seq", sequenceName = "sessao_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sessao_seq")
    private Integer id;

    private LocalDateTime dataInicio;

    private Integer minutosExpiracao;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pauta pauta;

    public Sessao pauta(Pauta pauta) {
        this.pauta = pauta;
        return this;
    }
}
