package com.lmg.desafiojavaspringboot.api.assembler;

import com.lmg.desafiojavaspringboot.api.dto.PautaDTO;
import com.lmg.desafiojavaspringboot.api.model.Pauta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PautaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PautaDTO toModel(Pauta pauta) {
        return modelMapper.map(pauta, PautaDTO.class);
    }

    public List<PautaDTO> toCollectionDTO(List<Pauta> pautas) {
        return pautas.stream()
                .map(pauta -> toModel(pauta))
                .collect(Collectors.toList());
    }
}
