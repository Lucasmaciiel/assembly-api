package com.lmg.assembly.domain.mapper;

import com.lmg.assembly.domain.dto.PautaDTO;
import com.lmg.assembly.domain.form.PautaForm;
import com.lmg.assembly.infrastructure.model.Pauta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PautaModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PautaDTO toModel(Pauta pauta) {
        return modelMapper.map(pauta, PautaDTO.class);
    }

    public Pauta toModel(PautaForm pautaForm){
        return modelMapper.map(pautaForm, Pauta.class);
    }

    public List<PautaDTO> toCollectionDTO(List<Pauta> pautas) {
        return pautas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
