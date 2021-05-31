package com.lmg.desafiojavaspringboot.api.dto;

import com.lmg.desafiojavaspringboot.api.model.Pauta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotacaoDTO {

    private static final long serialVersionUID = -6641295645471857940L;

    private Pauta pauta;
    private Integer totalSim;
    private Integer totalNao;
    private Integer totalVotos;
    private Integer totalSessoes;
}
