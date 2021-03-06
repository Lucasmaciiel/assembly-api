package com.lmg.assembly.domain.controller;

import com.lmg.assembly.domain.dto.PautaDTO;
import com.lmg.assembly.domain.form.PautaForm;
import com.lmg.assembly.domain.mapper.PautaMapper;
import com.lmg.assembly.domain.service.PautaService;
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
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("pauta")
@Api (value = "Pauta", tags = {"Pauta"})
public class PautaController {

    private final PautaService pautaService;
    private final PautaMapper pautaMapper;

    @ApiOperation(value = "Cadastra uma Pauta", nickname = "Pauta teste")
    @PostMapping
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorna quando uma pauta é criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public ResponseEntity<PautaDTO> save(@Valid @RequestBody PautaForm pautaForm) {
        var pauta = pautaMapper.toModel(pautaForm);
        return new ResponseEntity<>(pautaMapper.toModel(this.pautaService.save(pauta)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Busca uma pauta por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a pauta foi encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a pauta não existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PautaDTO> findById(@PathVariable("id") Integer id) {
        var obj = this.pautaService.findById(id);
        return new ResponseEntity<>(pautaMapper.toModel(obj), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta uma pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Retorna quando a pauta é deletada com sucesso"),
            @ApiResponse(code = 404, message = "Retorna quando a pauta não existe")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@NotNull @PathVariable("id") Integer id) {
        this.pautaService.delete(id);
    }

    @ApiOperation(value = "Retorna uma lista de Pautas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna quando a pauta foi encontrada com sucesso ou a lista de pauta está vazia")
    })
    @GetMapping
    public ResponseEntity<List<PautaDTO>> findAll() {
        return new ResponseEntity<>(pautaMapper.toCollectionDTO(pautaService.findAll()), HttpStatus.OK);
    }
}
