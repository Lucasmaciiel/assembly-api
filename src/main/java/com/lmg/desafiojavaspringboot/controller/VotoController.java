package com.lmg.desafiojavaspringboot.controller;

import com.lmg.desafiojavaspringboot.model.Voto;
import com.lmg.desafiojavaspringboot.service.VotoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("voto")
public class VotoController {

    private final VotoService votoService;

    @ApiOperation(value = "Cria um voto")
    @PostMapping("/pautas/{idPauta}/sessoes/{idSessao}/votos")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Voto save(@PathVariable Integer idPauta, @PathVariable Integer idSessao, @RequestBody Voto voto) {
        return votoService.createVoto(idPauta, idSessao, voto);
    }

    @ApiOperation(value = "Retorna todos os votos")
    @GetMapping("/pautas/sessoes/votos")
    public ResponseEntity<List<Voto>> findAll() {
        List<Voto> votos = votoService.findAll();
        return new ResponseEntity<>(votos, HttpStatus.OK);
    }

    @ApiOperation(value = "Busca voto pelo ID")
    @GetMapping("/pautas/sessoes/votos/{id}")
    public ResponseEntity<Voto> findById(@PathVariable Integer id) {
        Optional<Voto> votos = Optional.ofNullable(this.votoService.findById(id));
        return votos.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Busca voto pelo ID da Pauta")
    @GetMapping("/pautas/{id}/sessoes/votos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Voto> findVotosByPautaId(@PathVariable Integer id) {
        return votoService.findVotosByPautaId(id);
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