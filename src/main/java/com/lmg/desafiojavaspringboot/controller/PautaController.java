package com.lmg.desafiojavaspringboot.controller;

import com.lmg.desafiojavaspringboot.model.Pauta;
import com.lmg.desafiojavaspringboot.service.PautaService;
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
@RequestMapping("pauta")
public class PautaController {

    private final PautaService pautaService;

    @ApiOperation(value = "Cadastra uma Pauta")
    @PostMapping
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorna quando uma pauta é criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public ResponseEntity<Pauta> save(@Valid @RequestBody Pauta pauta) {
        this.pautaService.save(pauta);
        return new ResponseEntity<>(pauta, HttpStatus.CREATED);
    }

    @CrossOrigin
    @ApiOperation(value = "Busca uma pauta por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a pauta foi encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a pauta não existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pauta> findById(@PathVariable("id") Integer id) {
        Optional<Pauta> pauta = this.pautaService.findById(id);
        return pauta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @ApiOperation(value = "Deleta uma pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a pauta é deletada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a pauta não existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<Pauta> pauta = this.pautaService.findById(id);
        return pauta.map(value -> {
            this.pautaService.delete(value);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @ApiOperation(value = "Retorna uma lista de Pautas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a pauta foi encontrada com sucesso ou a lista de pauta está vazia")
    })
    @GetMapping
    public ResponseEntity<List<Pauta>> findAll() {
        List<Pauta> pautas = pautaService.findAll();
        return new ResponseEntity<>(pautas, HttpStatus.OK);

    }
}
