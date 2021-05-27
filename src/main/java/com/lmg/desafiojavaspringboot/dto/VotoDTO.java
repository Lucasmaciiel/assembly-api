package com.lmg.desafiojavaspringboot.dto;

import com.lmg.desafiojavaspringboot.model.Pauta;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VotoDTO {

    private Integer id;
    private String cpfCooperado;
    private Boolean opcaoEscolhida;
    private Pauta pauta;

}
