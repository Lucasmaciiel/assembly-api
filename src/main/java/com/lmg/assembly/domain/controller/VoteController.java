package com.lmg.assembly.domain.controller;

import com.lmg.assembly.domain.dto.VoteDTO;
import com.lmg.assembly.domain.form.VoteForm;
import com.lmg.assembly.domain.mapper.VoteModelMapper;
import com.lmg.assembly.domain.service.VotoService;
import com.lmg.assembly.infrastructure.model.Vote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
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

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("voto")
@Api(value = "Voto", tags = {"Voto"})
public class VoteController {

    private final VotoService votoService;
    private final VoteModelMapper voteModelMapper;

    @ApiOperation(value = "Cria um voto")
    @PostMapping("/pautas/{idPauta}/sessoes/{idSessao}/votos")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorna quando um voto é realizado com sucesso")
    })
    public ResponseEntity<VoteDTO> save(@PathVariable Integer idPauta, @PathVariable Integer idSessao, @RequestBody VoteForm voteForm) {
        var vote = voteModelMapper.toModel(voteForm);
        Vote obj = votoService.createVoto(idPauta, idSessao, vote);
        return new ResponseEntity<>(voteModelMapper.toDTO(obj), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retorna todos os votos")
    @GetMapping("/pautas/sessoes/votos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando o voto foi encontrado com sucesso ou a lista de votos está vazia")
    })
    public ResponseEntity<List<VoteDTO>> findAll() {
        return new ResponseEntity<>(voteModelMapper.toCollectionDTO(votoService.findAll()), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca voto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando o voto foi encontrado com sucesso")
    })
    @GetMapping("/pautas/sessoes/votos/{id}")
    public ResponseEntity<VoteDTO> findById(@PathVariable Integer id) {
        Vote obj = this.votoService.findById(id);
        return new ResponseEntity<>(voteModelMapper.toDTO(obj), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca voto pelo ID da Pauta")
    @GetMapping("/pautas/{id}/sessoes/votos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VoteDTO> findVotosByPautaId(@PathVariable Integer id) {
        return voteModelMapper.toCollectionDTO(votoService.findVotosByPautaId(id));
    }

    @ApiOperation(value = "Deleta um Voto")
    @DeleteMapping("/pautas/sessoes/votos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Vote> voto = Optional.ofNullable(this.votoService.findById(id));
        return voto.map(value -> {
            this.votoService.delete(value.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}