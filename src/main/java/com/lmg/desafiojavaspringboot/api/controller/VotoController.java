package com.lmg.desafiojavaspringboot.api.controller;

import com.lmg.desafiojavaspringboot.api.assembler.VotoModelAssembler;
import com.lmg.desafiojavaspringboot.api.dto.VotoDTO;
import com.lmg.desafiojavaspringboot.api.model.Voto;
import com.lmg.desafiojavaspringboot.api.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("voto")
@Api(value = "Voto", tags = {"Voto"})
public class VotoController {

    private final VotoService votoService;
    private final VotoModelAssembler votoModelAssembler;

    @ApiOperation(value = "Cria um voto")
    @PostMapping("/pautas/{idPauta}/sessoes/{idSessao}/votos")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorna quando um voto é realizado com sucesso")
    })
    public ResponseEntity<VotoDTO> save(@PathVariable Integer idPauta, @PathVariable Integer idSessao, @RequestBody Voto voto) {
        Voto obj = votoService.createVoto(idPauta, idSessao, voto);
        return new ResponseEntity<>(votoModelAssembler.toModel(obj), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retorna todos os votos")
    @GetMapping("/pautas/sessoes/votos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando o voto foi encontrado com sucesso ou a lista de votos está vazia")
    })
    public ResponseEntity<List<VotoDTO>> findAll() {
        return new ResponseEntity<>(votoModelAssembler.toCollectionDTO(votoService.findAll()), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca voto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando o voto foi encontrado com sucesso")
    })
    @GetMapping("/pautas/sessoes/votos/{id}")
    public ResponseEntity<VotoDTO> findById(@PathVariable Integer id) {
        Voto obj = this.votoService.findById(id);
        return new ResponseEntity<>(votoModelAssembler.toModel(obj), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca voto pelo ID da Pauta")
    @GetMapping("/pautas/{id}/sessoes/votos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VotoDTO> findVotosByPautaId(@PathVariable Integer id) {
        return votoModelAssembler.toCollectionDTO(votoService.findVotosByPautaId(id));
    }

    @ApiOperation(value = "Deleta um Voto")
    @DeleteMapping("/pautas/sessoes/votos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Voto> voto = Optional.ofNullable(this.votoService.findById(id));
        return voto.map(value -> {
            this.votoService.delete(value.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}