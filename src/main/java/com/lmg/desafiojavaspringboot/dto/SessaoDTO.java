package com.lmg.desafiojavaspringboot.dto;

import com.lmg.desafiojavaspringboot.model.Pauta;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SessaoDTO {

    private Integer id;
    private LocalDateTime dataInicio;
    private Integer minutosExpiracao;
    private Pauta pauta;

}
