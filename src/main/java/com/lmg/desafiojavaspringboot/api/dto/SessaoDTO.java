package com.lmg.desafiojavaspringboot.api.dto;

import com.lmg.desafiojavaspringboot.api.model.Pauta;
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
