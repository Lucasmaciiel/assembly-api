package com.lmg.assembly.domain.service;

import com.lmg.assembly.common.exception.EntityNotFoundException;
import com.lmg.assembly.infrastructure.model.Session;
import com.lmg.assembly.infrastructure.repository.PautaRepository;
import com.lmg.assembly.infrastructure.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoService {

    public static final String PAUTA_NOT_FOUND = "Pauta não encontrada com o ID: ";
    public static final String SESSION_NOT_FOUND = "Sessão não encontrada com o ID: ";

    private final SessionRepository sessionRepository;
    private final PautaRepository pautaRepository;

    public Session createSession(Integer id, Session session) {
        var pauta = pautaRepository.findById(id);
        if (!pauta.isPresent()) {
            throw new EntityNotFoundException(PAUTA_NOT_FOUND + id);
        }

        session.setPauta(pauta.get());
        return save(session);
    }

    public void delete(Integer id) {
        var sessao = this.findById(id);
        sessionRepository.delete(sessao);
    }

    public Session findById(Integer id) {
        var sessao = sessionRepository.findById(id);
        if (!sessao.isPresent()) {
            throw new EntityNotFoundException(SESSION_NOT_FOUND + id);
        }
        return sessao.get();
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session findByIdAndPautaId(Integer sessaoId, Integer pautaId) {
        var findByIdAndPautaId = sessionRepository.findByIdAndPautaId(sessaoId, pautaId);
        if (!findByIdAndPautaId.isPresent()) {
            throw new EntityNotFoundException("Sessão com o ID: " + sessaoId + ", não existe na Pauta de ID: " + pautaId);
        }
        return findByIdAndPautaId.get();
    }

    private Session save(final Session session) {
        if (session.getStartDate() == null) {
            session.setStartDate(LocalDateTime.now());
        }
        if (session.getSessionExpirationMinutes() == null) {
            session.setSessionExpirationMinutes(1);
        }

        return sessionRepository.save(session);
    }
}
