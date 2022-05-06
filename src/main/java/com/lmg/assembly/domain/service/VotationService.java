package com.lmg.assembly.domain.service;

import com.lmg.assembly.common.exception.BusinessException;
import com.lmg.assembly.common.exception.EntityNotFoundException;
import com.lmg.assembly.domain.dto.VotationDTO;
import com.lmg.assembly.infrastructure.model.Vote;
import com.lmg.assembly.infrastructure.repository.SessionRepository;
import com.lmg.assembly.infrastructure.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VotationService {

    public static final String VOTO_NAO_ENCONTRADO = "Voto não encontrado: ";
    public static final String VOTACAO_NAO_ENCONTRADA = "Votação não encontrada";

    private final VoteRepository voteRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public Vote save(final Vote vote) {
        this.verifyIfExists(vote);
        return voteRepository.save(vote);
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Transactional
    public void delete(Vote vote) {
        var votoById = voteRepository.findById(vote.getId());
        if (!votoById.isPresent()) {
            throw new EntityNotFoundException(VOTO_NAO_ENCONTRADO + vote.getId());
        }
        voteRepository.delete(vote);
    }

    public List<Vote> findVotosByPautaId(Integer id) {
        Optional<List<Vote>> findByPautaId = voteRepository.findByPautaId(id);

        if (!findByPautaId.isPresent()) {
            throw new EntityNotFoundException(VOTO_NAO_ENCONTRADO + id);
        }

        return findByPautaId.get();
    }

    public VotationDTO getResultVotacao(Integer id) {
        return buildVotacaoPauta(id);
    }


    /**
     * Método que constrói o resultado da votação
     *
     * @param id
     * @return
     */
    public VotationDTO buildVotacaoPauta(Integer id) {
        Optional<List<Vote>> votosByPauta = voteRepository.findByPautaId(id);
        if (!votosByPauta.isPresent() || votosByPauta.get().isEmpty()) {
            throw new EntityNotFoundException(VOTACAO_NAO_ENCONTRADA);
        }

        var votes = votosByPauta.get();

        var pauta = votes.iterator().next().getPauta();

        Long totalSessoes = sessionRepository.countByPautaId(pauta.getId());

        Integer total = votes.size();

        Integer totalSim = (int) votes.stream().filter(voto -> Boolean.TRUE.equals(voto.getChoice()))
                .count();

        Integer totalNao = total - totalSim;

        return VotationDTO.builder()
                .pauta(pauta)
                .totalVotes(total)
                .totalSessions(totalSessoes.intValue())
                .yesTotal(totalSim)
                .totalOfNo(totalNao)
                .build();
    }

    private void verifyIfExists(final Vote vote) throws BusinessException {
        var votoByCpfAndPauta = voteRepository.findByCpf(vote.getCpf());

        if (votoByCpfAndPauta.isPresent() && (vote.isNew() || isNotUnique(vote, votoByCpfAndPauta.get()))) {
            throw new BusinessException(null, null);
        }
    }

    private boolean isNotUnique(Vote vote, Vote voteByCpfAndPauta) {
        return vote.alreadyExist() && !voteByCpfAndPauta.equals(vote);
    }

}