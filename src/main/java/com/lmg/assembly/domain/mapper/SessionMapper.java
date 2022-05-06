package com.lmg.assembly.domain.mapper;

import com.lmg.assembly.domain.dto.SessionDTO;
import com.lmg.assembly.domain.form.SessionForm;
import com.lmg.assembly.infrastructure.model.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionMapper {

    @Autowired
    private ModelMapper modelMapper;

    public SessionDTO toDTO(Session obj) {
        return modelMapper.map(obj, SessionDTO.class);
    }

    public Session toModel(SessionForm obj) {
        return modelMapper.map(obj, Session.class);
    }

    public List<SessionDTO> toCollectionDTO(List<Session> sessoes) {
        return sessoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
