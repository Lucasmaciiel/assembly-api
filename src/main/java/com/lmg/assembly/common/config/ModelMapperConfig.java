package com.lmg.assembly.common.config;

import com.lmg.assembly.domain.dto.VoteDTO;
import com.lmg.assembly.infrastructure.model.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        //Mapeia as propriedades que o ModelMapper não consegue identificar pelo nome
        modelMapper.createTypeMap(Vote.class, VoteDTO.class)
                .addMapping(Vote::getCpf, VoteDTO::setCpfCooperated)
                .addMapping(Vote::getChoice, VoteDTO::setChosenOption);
        return modelMapper;
    }


}
