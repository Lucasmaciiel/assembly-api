package com.lmg.desafiojavaspringboot.controller;

import com.lmg.desafiojavaspringboot.dto.VotacaoDTO;
import com.lmg.desafiojavaspringboot.service.VotacaoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("votacao")
public class VotacaoController {

    private final VotacaoService votacaoService;

    @ApiOperation(value = "Retorna o resultado da votação")
    @GetMapping("pautas/{id}/votacao")
    @ResponseStatus(code = HttpStatus.OK)
    public VotacaoDTO findVotosByPautaId(@PathVariable Integer id) {
        return votacaoService.getResultVotacao(id);
    }
}
