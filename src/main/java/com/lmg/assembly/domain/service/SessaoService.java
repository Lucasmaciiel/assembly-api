package com.lmg.assembly.domain.service;
import com.lmg.assembly.common.exception.EntityNotFoundException;
import com.lmg.assembly.infrastructure.model.Pauta;
import com.lmg.assembly.infrastructure.model.Session;
import com.lmg.assembly.infrastructure.repository.PautaRepository;
import com.lmg.assembly.infrastructure.repository.SessaoRepository;
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

    public Session createSession(Integer id, Session session) {
        Optional<Pauta> pauta = pautaRepository.findById(id);
        if (!pauta.isPresent()) {
            throw new EntityNotFoundException("Pauta não encontrada com o ID: " + id);
        }
        session.setPauta(pauta.get());
        return save(session);
    }

    private Session save(final Session session) {
        if (session.getDataInicio() == null) {
            session.setDataInicio(LocalDateTime.now());
        }
        if (session.getMinutosExpiracao() == null) {
            session.setMinutosExpiracao(1);
        }

        return sessaoRepository.save(session);

    }

    public void delete(Integer id) {
        Optional<Session> sessao = sessaoRepository.findById(id);
        if (!sessao.isPresent()) {
            throw new EntityNotFoundException("Sessão não encontrada: " + id);
        }
        sessaoRepository.delete(sessao.get());
    }

    public Session findById(Integer id) {
        Optional<Session> sessao = sessaoRepository.findById(id);
        if (!sessao.isPresent()) {
            throw new EntityNotFoundException("Sessão não encontrada com o ID: " + id);
        }
        return sessao.get();
    }

    public List<Session> findAll() {
        return sessaoRepository.findAll();
    }

    public Session findByIdAndPautaId(Integer sessaoId, Integer pautaId) {
        Optional<Session> findByIdAndPautaId = sessaoRepository.findByIdAndPautaId(sessaoId, pautaId);
        if (!findByIdAndPautaId.isPresent()) {
            throw new EntityNotFoundException("Sessão com o ID: " + sessaoId + ", não existe na Pauta de ID: " + pautaId);
        }
        return findByIdAndPautaId.get();
    }
}
