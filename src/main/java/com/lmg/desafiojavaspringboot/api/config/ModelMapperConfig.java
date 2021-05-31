package com.lmg.desafiojavaspringboot.api.config;

import com.lmg.desafiojavaspringboot.api.dto.VotoDTO;
import com.lmg.desafiojavaspringboot.api.model.Voto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        //Mapeia as propriedades que o ModelMapper não consegue identificar pelo nome
        modelMapper.createTypeMap(Voto.class, VotoDTO.class)
                .addMapping(Voto::getCpf, VotoDTO::setCpfCooperado)
                .addMapping(Voto::getEscolha, VotoDTO::setOpcaoEscolhida);
        return modelMapper;
    }


}
