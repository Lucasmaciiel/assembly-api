package com.lmg.assembly.domain.controller;

import com.lmg.assembly.domain.dto.SessionDTO;
import com.lmg.assembly.domain.form.SessionForm;
import com.lmg.assembly.domain.mapper.SessionModelMapper;
import com.lmg.assembly.domain.service.SessaoService;
import com.lmg.assembly.infrastructure.model.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("sessao")
@Api(value = "Sessão", tags = {"Sessão"})
public class SessionController {

    private final SessaoService sessaoService;
    private final SessionModelMapper sessionModelMapper;

    @ApiOperation(value = "Abre uma sessão de votação em uma Pauta")
    @PostMapping("pautas/{id}/sessoes")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorna quando uma sessão é criada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a pauta não existe")
    })
    public ResponseEntity<SessionDTO> save(@PathVariable Integer id, @Valid @RequestBody SessionForm sessionForm) {
        var session = sessionModelMapper.toModel(sessionForm);
        Session obj = sessaoService.createSession(id, session);
        return new ResponseEntity<>(sessionModelMapper.toDTO(obj), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Busca uma sessão por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a sessão foi encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a sessão não existe")
    })
    @GetMapping("pautas/sessoes/{id}")
    public ResponseEntity<SessionDTO> findById(@PathVariable Integer id) {
        Session obj = this.sessaoService.findById(id);
        return new ResponseEntity<>(sessionModelMapper.toDTO(obj), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta uma sessão")
    @DeleteMapping("pautas/sessoes/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Session> sessao = Optional.ofNullable(this.sessaoService.findById(id));
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
    public ResponseEntity<List<SessionDTO>> findAll() {
        return new ResponseEntity<>(sessionModelMapper.toCollectionDTO(sessaoService.findAll()), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca sessões por Pauta")
    @GetMapping("pautas/{id}/sessoes/{idSessao}")
    @ResponseStatus(code = HttpStatus.OK)
    public SessionDTO findSessaoByIdAndPautaId(@PathVariable Integer id, @PathVariable Integer idSessao) {
        Session obj = sessaoService.findByIdAndPautaId(idSessao, id);
        return sessionModelMapper.toDTO(obj);
    }
}
