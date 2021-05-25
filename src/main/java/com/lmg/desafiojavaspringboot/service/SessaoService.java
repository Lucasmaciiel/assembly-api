package com.lmg.desafiojavaspringboot.service;
import com.lmg.desafiojavaspringboot.exception.EntidadeNaoEncontradaException;
import com.lmg.desafiojavaspringboot.model.Pauta;
import com.lmg.desafiojavaspringboot.model.Sessao;
import com.lmg.desafiojavaspringboot.repository.PautaRepository;
import com.lmg.desafiojavaspringboot.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;

    public Sessao createSession(Integer id, Sessao sessao) {
        Optional<Pauta> pauta = pautaRepository.findById(id);
        if (!pauta.isPresent()) {
            throw new EntidadeNaoEncontradaException("Pauta não encontrada: " + id);
        }
        sessao.setPauta(pauta.get());
        return save(sessao);
    }

    private Sessao save(final Sessao sessao) {
        if (sessao.getStartDate() == null) {
            sessao.setStartDate(LocalDateTime.now());
        }
        if (sessao.getMinutesValidity() == null) {
            sessao.setMinutesValidity(1);
        }

        return sessaoRepository.save(sessao);

    }

    public void delete(Integer id) {
        Optional<Sessao> sessao = sessaoRepository.findById(id);
        if (!sessao.isPresent()) {
            throw new EntidadeNaoEncontradaException("Sessão não encontrada: " + id);
        }
        sessaoRepository.delete(sessao.get());
    }

    public Sessao findById(Integer id) {
        Optional<Sessao> sessao = sessaoRepository.findById(id);
        if (!sessao.isPresent()) {
            throw new EntidadeNaoEncontradaException("Sessão não encontrada: " + id);
        }
        return sessao.get();
    }

    public List<Sessao> findAll() {
        return sessaoRepository.findAll();
    }

    public Sessao findByIdAndPautaId(Integer sessaoId, Integer pautaId) {
        Optional<Sessao> findByIdAndPautaId = sessaoRepository.findByIdAndPautaId(sessaoId, pautaId);
        if (!findByIdAndPautaId.isPresent()) {
            throw new EntidadeNaoEncontradaException("Sessão não encontrada: " + sessaoId);
        }
        return findByIdAndPautaId.get();
    }
}
