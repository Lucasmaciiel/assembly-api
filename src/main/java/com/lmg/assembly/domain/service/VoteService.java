package com.lmg.assembly.domain.service;

import com.lmg.assembly.common.exception.CpfCanNotExecuteThisOperationException;
import com.lmg.assembly.common.exception.CpfInvalidException;
import com.lmg.assembly.common.exception.SessionExpiredException;
import com.lmg.assembly.common.exception.VoteAlreadyExistsException;
import com.lmg.assembly.domain.dto.ValidationCpfDTO;
import com.lmg.assembly.infrastructure.model.Session;
import com.lmg.assembly.infrastructure.model.Vote;
import com.lmg.assembly.infrastructure.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VoteService {

    private static final String CPF_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";
    public static final String VOTE_NOT_FOUND = "Voto não encontrado com ID: ";
    public static final String EXPIRED_SESSION = "Tempo de sessão expirada";
    public static final String VOTE_ALREADY_EXISTS_FOR_THIS_CPF = "Voto ja existente para o CPF: ";
    public static final String NECESSARY_TO_BE_COOPERATE = "Este CPF não pode executar essa operação, é necessário ser cooperado";
    public static final String INVALID_CPF = "CPF inválido";

    @Value("${app.integracao.cpf.url}")
    private String urlCpfValidator = "";

    private final VoteRepository voteRepository;
    private final RestTemplate restTemplate;
    private final SessionService sessionService;

    public VoteService(VoteRepository voteRepository, RestTemplate restTemplate, SessionService sessionService) {
        this.voteRepository = voteRepository;
        this.restTemplate = restTemplate;
        this.sessionService = sessionService;
    }

    public Vote findById(Integer id) {
        var vote = voteRepository.findById(id);
        if (!vote.isPresent()) {
            throw new com.lmg.assembly.common.exception.EntityNotFoundException(VOTE_NOT_FOUND + id);
        }
        return vote.get();
    }

    public Vote createVoto(Integer idPauta, Integer idSessao, Vote vote) {
        var sessao = sessionService.findByIdAndPautaId(idSessao, idPauta);

        vote.setPauta(sessao.getPauta());
        return verifyAndSave(sessao, vote);
    }

    private Vote verifyAndSave(final Session session, final Vote vote) {
        verifyVoto(session, vote);
        return voteRepository.save(vote);
    }

    protected void verifyVoto(final Session session, final Vote vote) {

        LocalDateTime deadline = session.getStartDate().plusMinutes(session.getSessionExpirationMinutes());
        if (LocalDateTime.now().isAfter(deadline)) {
            throw new SessionExpiredException(EXPIRED_SESSION);
        }

        this.cpfAbleToVote(vote);
        this.voteAlreadyExists(vote);
    }

    protected void voteAlreadyExists(final Vote vote) {
        Optional<Vote> voteByCpfAndPauta = voteRepository.findByCpfAndPautaId(vote.getCpf(), vote.getPauta().getId());

        if (voteByCpfAndPauta.isPresent()) {
            throw new VoteAlreadyExistsException(VOTE_ALREADY_EXISTS_FOR_THIS_CPF + vote.getCpf());
        }
    }

    protected void cpfAbleToVote(final Vote vote) {
        ResponseEntity<ValidationCpfDTO> cpfValidation = getCpfValidation(vote);
        if (HttpStatus.OK.equals(cpfValidation.getStatusCode())) {

            if (CPF_UNABLE_TO_VOTE.equalsIgnoreCase(Objects.requireNonNull(cpfValidation.getBody()).getStatus())) {
                throw new CpfCanNotExecuteThisOperationException(NECESSARY_TO_BE_COOPERATE);
            }
        } else {
            throw new CpfInvalidException(INVALID_CPF);
        }
    }

    private ResponseEntity<ValidationCpfDTO> getCpfValidation(final Vote vote) {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate
                .exchange(urlCpfValidator.concat("/").concat(vote.getCpf()),
                HttpMethod.GET, entity,
                ValidationCpfDTO.class);
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public void delete(Integer id) {
        var vote = this.findById(id);
        voteRepository.delete(vote);
    }

    public List<Vote> findVotosByPautaId(Integer id) {
        Optional<List<Vote>> findByPautaId = voteRepository.findByPautaId(id);

        if (!findByPautaId.isPresent()) {
            throw new EntityNotFoundException(VOTE_NOT_FOUND + id);
        }

        return findByPautaId.get();
    }

}