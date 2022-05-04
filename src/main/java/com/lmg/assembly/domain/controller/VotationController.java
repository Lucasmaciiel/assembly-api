package com.lmg.assembly.domain.controller;

import com.lmg.assembly.domain.dto.VotationDTO;
import com.lmg.assembly.domain.service.VotationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("votacao")
@Api(value = "Votação", tags = {"Votação"})
public class VotationController {

    private final VotationService votationService;

    @ApiOperation(value = "Retorna o resultado da votação")
    @GetMapping("pautas/{idPauta}/votacao")
    @ResponseStatus(code = HttpStatus.OK)
    public VotationDTO findVotosByPautaId(@PathVariable Integer idPauta) {
        return votationService.getResultVotacao(idPauta);
    }
}
