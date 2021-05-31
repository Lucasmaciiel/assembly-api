package com.lmg.desafiojavaspringboot.api.assembler;

import com.lmg.desafiojavaspringboot.api.dto.SessaoDTO;
import com.lmg.desafiojavaspringboot.api.model.Sessao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public SessaoDTO toModel(Sessao obj) {
        return modelMapper.map(obj, SessaoDTO.class);
    }

    public List<SessaoDTO> toCollectionDTO(List<Sessao> sessoes) {
        return sessoes.stream()
                .map(sessao -> toModel(sessao))
                .collect(Collectors.toList());
    }
}
