package com.lmg.assembly.domain.dto;

import com.lmg.assembly.infrastructure.model.Pauta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotationDTO {

    private static final long serialVersionUID = -6641295645471857940L;

    private Pauta pauta;
    private Integer yesTotal;
    private Integer totalOfNo;
    private Integer totalVotes;
    private Integer totalSessions;
}
