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

    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;

    public Vote save(final Vote vote) {
        verifyIfExists(vote);
        return votoRepository.save(vote);
    }

    private void verifyIfExists(final Vote vote) throws BusinessException {
        Optional<Vote> votoByCpfAndPauta = votoRepository.findByCpf(vote.getCpf());

        if (votoByCpfAndPauta.isPresent() && (vote.isNew() || isNotUnique(vote, votoByCpfAndPauta.get()))) {
            throw new BusinessException(null, null);
        }
    }

    private boolean isNotUnique(Vote vote, Vote voteByCpfAndPauta) {
        return vote.alreadyExist() && !voteByCpfAndPauta.equals(vote);
    }

    public List<Vote> findAll() {
        return votoRepository.findAll();
    }

    public void delete(Vote vote) {
        Optional<Vote> votoById = votoRepository.findById(vote.getId());
        if (!votoById.isPresent()) {
            throw new EntityNotFoundException("Voto não encontrado: " + vote.getId());
        }
        votoRepository.delete(vote);
    }

    public List<Vote> findVotosByPautaId(Integer id) {
        Optional<List<Vote>> findByPautaId = votoRepository.findByPautaId(id);

        if (!findByPautaId.isPresent()) {
            throw new EntityNotFoundException("Voto não encontrado: " + id);
        }

        return findByPautaId.get();
    }

    public VotationDTO getResultVotacao(Integer id){
        return buildVotacaoPauta(id);
    }


    public VotationDTO buildVotacaoPauta(Integer id) {
        Optional<List<Vote>> votosByPauta = votoRepository.findByPautaId(id);
        if (!votosByPauta.isPresent() || votosByPauta.get().isEmpty()) {
            throw new EntityNotFoundException("Votação não encontrada");
        }

        var pauta = votosByPauta.get().iterator().next().getPauta();

        Long totalSessoes = sessaoRepository.countByPautaId(pauta.getId());

        Integer total = votosByPauta.get().size();

        Integer totalSim = (int) votosByPauta.get().stream().filter(voto -> Boolean.TRUE.equals(voto.getEscolha()))
                .count();

        Integer totalNao = total - totalSim;

        return VotationDTO.builder()
                .pauta(pauta)
                .totalVotos(total)
                .totalSessoes(totalSessoes.intValue())
                .totalSim(totalSim)
                .totalNao(totalNao).build();
    }

}