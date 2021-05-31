package com.lmg.desafiojavaspringboot.api.controller;

import com.lmg.desafiojavaspringboot.api.assembler.SessaoModelAssembler;
import com.lmg.desafiojavaspringboot.api.dto.SessaoDTO;
import com.lmg.desafiojavaspringboot.api.model.Sessao;
import com.lmg.desafiojavaspringboot.api.service.SessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("sessao")
@Api(value = "Sessão", tags = {"Sessão"})
public class SessaoController {

    private final SessaoService sessaoService;
    private final SessaoModelAssembler sessaoModelAssembler;

    @ApiOperation(value = "Abre uma sessão de votação em uma Pauta")
    @PostMapping("pautas/{id}/sessoes")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorna quando uma sessão é criada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a pauta não existe")
    })
    public ResponseEntity<SessaoDTO> save(@PathVariable Integer id, @Valid @RequestBody Sessao sessao) {
        Sessao obj = sessaoService.createSession(id, sessao);
        return new ResponseEntity<>(sessaoModelAssembler.toModel(obj), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Busca uma sessão por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a sessão foi encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a sessão não existe")
    })
    @GetMapping("pautas/sessoes/{id}")
    public ResponseEntity<SessaoDTO> findById(@PathVariable Integer id) {
        Sessao obj = this.sessaoService.findById(id);
        return new ResponseEntity<>(sessaoModelAssembler.toModel(obj), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta uma sessão")
    @DeleteMapping("pautas/sessoes/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Sessao> sessao = Optional.ofNullable(this.sessaoService.findById(id));
        return sessao.map(value -> {
            this.sessaoService.delete(value.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Retorna uma lista de sessões")
    @GetMapping("pautas/sessoes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a sessão foi encontrada com sucesso ou a lista de sessões está vazia")
    })
    public ResponseEntity<List<SessaoDTO>> findAll() {
        return new ResponseEntity<>(sessaoModelAssembler.toCollectionDTO(sessaoService.findAll()), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca sessões por Pauta")
    @GetMapping("pautas/{id}/sessoes/{idSessao}")
    @ResponseStatus(code = HttpStatus.OK)
    public SessaoDTO findSessaoByIdAndPautaId(@PathVariable Integer id, @PathVariable Integer idSessao) {
        Sessao obj = sessaoService.findByIdAndPautaId(idSessao, id);
        return sessaoModelAssembler.toModel(obj);
    }
}
