package com.lmg.desafiojavaspringboot.controller;

import com.lmg.desafiojavaspringboot.model.Sessao;
import com.lmg.desafiojavaspringboot.service.SessaoService;
import io.swagger.annotations.ApiOperation;
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
public class SessaoController {

    private final SessaoService sessaoService;

    @ApiOperation(value = "Abre uma sessão de votação em uma Pauta")
    @PostMapping("pautas/{id}/sessoes")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Sessao save(@PathVariable Integer id, @Valid @RequestBody Sessao sessao) {
        return sessaoService.createSession(id, sessao);
    }

    @ApiOperation(value = "Busca uma sessão por ID")
    @GetMapping("pautas/sessoes/{id}")
    public ResponseEntity<Sessao> findById(@PathVariable Integer id) {
        Optional<Sessao> sessao = Optional.ofNullable(this.sessaoService.findById(id));
        return sessao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
    public ResponseEntity<List<Sessao>> findAll() {
        List<Sessao> sessoes = sessaoService.findAll();
        return new ResponseEntity<>(sessoes, HttpStatus.OK);
    }

    @ApiOperation(value = "Busca sessões por Pauta")
    @GetMapping("pautas/{id}/sessoes/{idSessao}")
    @ResponseStatus(code = HttpStatus.OK)
    public Sessao findSessaoByIdAndPautaId(@PathVariable Integer id, @PathVariable Integer idSessao) {
        return sessaoService.findByIdAndPautaId(idSessao, id);
    }
}
