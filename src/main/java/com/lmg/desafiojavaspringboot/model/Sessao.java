package com.lmg.desafiojavaspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "sessao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sessao {

    @Id
    @SequenceGenerator(name = "sessao_seq", sequenceName = "sessao_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sessao_seq")
    private Integer id;

    private LocalDateTime startDate;

    private Integer minutesValidity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pauta pauta;

    public Sessao pauta(Pauta pauta) {
        this.pauta = pauta;
        return this;
    }
}
