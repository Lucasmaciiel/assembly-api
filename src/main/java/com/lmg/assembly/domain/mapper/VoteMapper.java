package com.lmg.assembly.domain.mapper;

import com.lmg.assembly.domain.dto.VoteDTO;
import com.lmg.assembly.domain.form.VoteForm;
import com.lmg.assembly.infrastructure.model.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public VoteDTO toDTO(Vote vote) {
        return modelMapper.map(vote, VoteDTO.class);
    }
    public Vote toModel(VoteForm vote) {
        return modelMapper.map(vote, Vote.class);
    }

    public List<VoteDTO> toCollectionDTO(List<Vote> votes) {
        return votes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


}
