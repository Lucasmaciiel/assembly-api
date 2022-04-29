package com.lmg.assembly.domain.dto;

import com.lmg.assembly.infrastructure.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SessionDTO {

    private Integer id;
    private LocalDateTime dataInicio;
    private Integer minutosExpiracao;
    private Pauta pauta;

}
