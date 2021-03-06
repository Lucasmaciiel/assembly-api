package com.lmg.assembly.domain.controller;

import com.lmg.assembly.domain.dto.VoteDTO;
import com.lmg.assembly.domain.form.VoteForm;
import com.lmg.assembly.domain.mapper.VoteMapper;
import com.lmg.assembly.domain.service.VoteService;
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

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("voto")
@Api(value = "Voto", tags = {"Voto"})
public class VoteController {

    private final VoteService voteService;
    private final VoteMapper voteMapper;

    @ApiOperation(value = "Cria um voto")
    @PostMapping("/pautas/{idPauta}/sessoes/{idSessao}/votos")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorna quando um voto é realizado com sucesso")
    })
    public ResponseEntity<VoteDTO> save(@PathVariable Integer idPauta, @PathVariable Integer idSessao, @RequestBody VoteForm voteForm) {
        var vote = voteMapper.toModel(voteForm);
        Vote obj = voteService.createVoto(idPauta, idSessao, vote);
        return new ResponseEntity<>(voteMapper.toDTO(obj), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retorna todos os votos")
    @GetMapping("/pautas/sessoes/votos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando o voto foi encontrado com sucesso ou a lista de votos está vazia")
    })
    public ResponseEntity<List<VoteDTO>> findAll() {
        return new ResponseEntity<>(voteMapper.toCollectionDTO(voteService.findAll()), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca voto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando o voto foi encontrado com sucesso")
    })
    @GetMapping("/pautas/sessoes/votos/{id}")
    public ResponseEntity<VoteDTO> findById(@PathVariable Integer id) {
        Vote obj = this.voteService.findById(id);
        return new ResponseEntity<>(voteMapper.toDTO(obj), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca voto pelo ID da Pauta")
    @GetMapping("/pautas/{id}/sessoes/votos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VoteDTO> findVotosByPautaId(@PathVariable Integer id) {
        return voteMapper.toCollectionDTO(voteService.findVotosByPautaId(id));
    }

    @ApiOperation(value = "Deleta um voto")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Retorna quando um voto é deletado com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando o voto não existe")
    })
    @DeleteMapping("/pautas/sessoes/votos/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@NotNull @PathVariable Integer id) {
        this.voteService.delete(id);
    }
}