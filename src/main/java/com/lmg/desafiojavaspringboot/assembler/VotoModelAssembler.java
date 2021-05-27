package com.lmg.desafiojavaspringboot.assembler;

import com.lmg.desafiojavaspringboot.dto.VotoDTO;
import com.lmg.desafiojavaspringboot.model.Voto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VotoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public VotoDTO toModel(Voto voto) {
        return modelMapper.map(voto, VotoDTO.class);
//        VotoDTO votoDTO = new VotoDTO();
//        votoDTO.setId(voto.getId());
//        votoDTO.setCpfCooperado(voto.getCpf());
//        votoDTO.setOpcaoEscolhida(voto.getEscolha());
//        votoDTO.setPauta(voto.getPauta());
//        return votoDTO;
    }

    public List<VotoDTO> toCollectionDTO(List<Voto> votos) {
        return votos.stream()
                .map(voto -> toModel(voto))
                .collect(Collectors.toList());
    }


}
