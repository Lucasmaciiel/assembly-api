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

    @ApiOperation(value = "Create a Pauta")
    @PostMapping
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Returns when a pauta is successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<Pauta> save(@Valid @RequestBody Pauta pauta) {
        this.pautaService.save(pauta);
        return new ResponseEntity<>(pauta, HttpStatus.CREATED);
    }

    @CrossOrigin
    @ApiOperation(value = "Search for a pauta by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the pauta was found successfully"),
            @ApiResponse(code = 404, message = "Returns when the pauta does not exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pauta> findById(@PathVariable("id") Integer id) {
        Optional<Pauta> product = this.pautaService.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @ApiOperation(value = "Delete a pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the pauta is successfully deleted"),
            @ApiResponse(code = 404, message = "Returns when the pauta does not exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<Pauta> product = this.pautaService.findById(id);
        return product.map(value -> {
            this.pautaService.delete(value);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @ApiOperation(value = "Pauta list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the pauta were found successfully or the pauta list is empty")
    })
    @GetMapping
    public ResponseEntity<List<Pauta>> findAll() {
        List<Pauta> products = this.pautaService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }
}
