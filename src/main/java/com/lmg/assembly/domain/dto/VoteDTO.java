package com.lmg.assembly.domain.dto;

import com.lmg.assembly.infrastructure.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VoteDTO {

    private Integer id;
    private String cpfCooperado;
    private Boolean opcaoEscolhida;
    private Pauta pauta;

}
