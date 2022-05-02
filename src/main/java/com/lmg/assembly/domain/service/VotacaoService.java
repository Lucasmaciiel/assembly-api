package com.lmg.assembly.domain.service;

import com.lmg.assembly.common.exception.BusinessException;
import com.lmg.assembly.common.exception.EntityNotFoundException;
import com.lmg.assembly.domain.dto.VotationDTO;
import com.lmg.assembly.infrastructure.model.Vote;
import com.lmg.assembly.infrastructure.repository.SessaoRepository;
import com.lmg.assembly.infrastructure.repository.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VotacaoService {

    public static final String VOTO_NAO_ENCONTRADO = "Voto não encontrado: ";
    public static final String VOTACAO_NAO_ENCONTRADA = "Votação não encontrada";

    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;

    public Vote save(final Vote vote) {
        this.verifyIfExists(vote);
        return votoRepository.save(vote);
    }

    public List<Vote> findAll() {
        return votoRepository.findAll();
    }

    public void delete(Vote vote) {
        var votoById = votoRepository.findById(vote.getId());
        if (!votoById.isPresent()) {
            throw new EntityNotFoundException(VOTO_NAO_ENCONTRADO + vote.getId());
        }
        votoRepository.delete(vote);
    }

    public List<Vote> findVotosByPautaId(Integer id) {
        Optional<List<Vote>> findByPautaId = votoRepository.findByPautaId(id);

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
        Optional<List<Vote>> votosByPauta = votoRepository.findByPautaId(id);
        if (!votosByPauta.isPresent() || votosByPauta.get().isEmpty()) {
            throw new EntityNotFoundException(VOTACAO_NAO_ENCONTRADA);
        }

        var votes = votosByPauta.get();

        var pauta = votes.iterator().next().getPauta();

        Long totalSessoes = sessaoRepository.countByPautaId(pauta.getId());

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
        var votoByCpfAndPauta = votoRepository.findByCpf(vote.getCpf());

        if (votoByCpfAndPauta.isPresent() && (vote.isNew() || isNotUnique(vote, votoByCpfAndPauta.get()))) {
            throw new BusinessException(null, null);
        }
    }

    private boolean isNotUnique(Vote vote, Vote voteByCpfAndPauta) {
        return vote.alreadyExist() && !voteByCpfAndPauta.equals(vote);
    }

}