package com.lmg.desafiojavaspringboot.service;

import com.lmg.desafiojavaspringboot.dto.VotacaoDTO;
import com.lmg.desafiojavaspringboot.exception.NegocioException;
import com.lmg.desafiojavaspringboot.exception.EntidadeNaoEncontradaException;
import com.lmg.desafiojavaspringboot.model.Voto;
import com.lmg.desafiojavaspringboot.repository.SessaoRepository;
import com.lmg.desafiojavaspringboot.repository.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VotacaoService {

    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;

    public Voto save(final Voto voto) {
        verifyIfExists(voto);
        return votoRepository.save(voto);
    }

    private void verifyIfExists(final Voto voto) throws NegocioException {
        Optional<Voto> votoByCpfAndPauta = votoRepository.findByCpf(voto.getCpf());

        if (votoByCpfAndPauta.isPresent() && (voto.isNew() || isNotUnique(voto, votoByCpfAndPauta.get()))) {
            throw new NegocioException(null, null);
        }
    }

    private boolean isNotUnique(Voto voto, Voto votoByCpfAndPauta) {
        return voto.alreadyExist() && !votoByCpfAndPauta.equals(voto);
    }

    public List<Voto> findAll() {
        return votoRepository.findAll();
    }

    public void delete(Voto voto) {
        Optional<Voto> votoById = votoRepository.findById(voto.getId());
        if (!votoById.isPresent()) {
            throw new EntidadeNaoEncontradaException("Voto não encontrado: " + voto.getId());
        }
        votoRepository.delete(voto);
    }

    public List<Voto> findVotosByPautaId(Integer id) {
        Optional<List<Voto>> findByPautaId = votoRepository.findByPautaId(id);

        if (!findByPautaId.isPresent()) {
            throw new EntidadeNaoEncontradaException("Voto não encontrado: " + id);
        }

        return findByPautaId.get();
    }

    public VotacaoDTO getResultVotacao(Integer id){
        return buildVotacaoPauta(id);
    }


    public VotacaoDTO buildVotacaoPauta(Integer id) {
        Optional<List<Voto>> votosByPauta = votoRepository.findByPautaId(id);
        if (!votosByPauta.isPresent() || votosByPauta.get().isEmpty()) {
            throw new EntidadeNaoEncontradaException("Votação não encontrada");
        }

        var pauta = votosByPauta.get().iterator().next().getPauta();

        Long totalSessoes = sessaoRepository.countByPautaId(pauta.getId());

        Integer total = votosByPauta.get().size();

        Integer totalSim = (int) votosByPauta.get().stream().filter(voto -> Boolean.TRUE.equals(voto.getEscolha()))
                .count();

        Integer totalNao = total - totalSim;

        return VotacaoDTO.builder()
                .pauta(pauta)
                .totalVotos(total)
                .totalSessoes(totalSessoes.intValue())
                .totalSim(totalSim)
                .totalNao(totalNao).build();
    }

}