package com.lmg.desafiojavaspringboot.api.controller;

import com.lmg.desafiojavaspringboot.api.dto.VotacaoDTO;
import com.lmg.desafiojavaspringboot.api.service.VotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("votacao")
@Api(value = "Votação", tags = {"Votação"})
public class VotacaoController {

    private final VotacaoService votacaoService;

    @ApiOperation(value = "Retorna o resultado da votação")
    @GetMapping("pautas/{id}/votacao")
    @ResponseStatus(code = HttpStatus.OK)
    public VotacaoDTO findVotosByPautaId(@PathVariable Integer id) {
        return votacaoService.getResultVotacao(id);
    }
}
