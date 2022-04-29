package com.lmg.assembly.domain.service;

import com.lmg.assembly.common.exception.CpfCanNotExecuteThisOperationException;
import com.lmg.assembly.common.exception.CpfInvalidException;
import com.lmg.assembly.common.exception.SessionExpiredException;
import com.lmg.assembly.common.exception.SessionInvalidException;
import com.lmg.assembly.common.exception.VoteAlreadyExistsException;
import com.lmg.assembly.domain.dto.ValidationCpfDTO;
import com.lmg.assembly.infrastructure.model.Session;
import com.lmg.assembly.infrastructure.model.Vote;
import com.lmg.assembly.infrastructure.repository.VotoRepository;
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
public class VotoService {

    private static final String CPF_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";
    public static final String VOTO_NAO_ENCONTRADO = "Voto não encontrado: ";

    @Value("${app.integracao.cpf.url}")
    private String urlCpfValidator = "";

    private final VotoRepository votoRepository;
    private final RestTemplate restTemplate;
    private final SessaoService sessaoService;

    public VotoService(VotoRepository votoRepository, RestTemplate restTemplate, SessaoService sessaoService) {
        this.votoRepository = votoRepository;
        this.restTemplate = restTemplate;
        this.sessaoService = sessaoService;
    }

    public Vote findById(Integer id) {
        Optional<Vote> findById = votoRepository.findById(id);
        if (!findById.isPresent()) {
            throw new EntityNotFoundException("Voto não encontrado com o ID: " + id);
        }
        return findById.get();
    }

    public Vote createVoto(Integer idPauta, Integer idSessao, Vote vote) {
        var sessao = sessaoService.findByIdAndPautaId(idSessao, idPauta);
        if (!idPauta.equals(sessao.getPauta().getId())) {
            throw new SessionInvalidException("Sessão inválida");
        }
        vote.setPauta(sessao.getPauta());
        return verifyAndSave(sessao, vote);
    }

    private Vote verifyAndSave(final Session session, final Vote vote) {
        verifyVoto(session, vote);
        return votoRepository.save(vote);
    }

    protected void verifyVoto(final Session session, final Vote vote) {

        LocalDateTime dataLimite = session.getDataInicio().plusMinutes(session.getMinutosExpiracao());
        if (LocalDateTime.now().isAfter(dataLimite)) {
            throw new SessionExpiredException("Tempo de sessão expirada");
        }

        cpfAbleToVote(vote);
        votoAlreadyExists(vote);
    }

    protected void votoAlreadyExists(final Vote vote) {
        Optional<Vote> votoByCpfAndPauta = votoRepository.findByCpfAndPautaId(vote.getCpf(), vote.getPauta().getId());

        if (votoByCpfAndPauta.isPresent()) {
            throw new VoteAlreadyExistsException("Voto ja existente para o CPF: " + vote.getCpf());
        }
    }

    protected void cpfAbleToVote(final Vote vote) {
        ResponseEntity<ValidationCpfDTO> cpfValidation = getCpfValidation(vote);
        if (HttpStatus.OK.equals(cpfValidation.getStatusCode())) {


            if (CPF_UNABLE_TO_VOTE.equalsIgnoreCase(Objects.requireNonNull(cpfValidation.getBody()).getStatus())) {
                throw new CpfCanNotExecuteThisOperationException("Este CPF não pode executar essa operação, é necessário ser cooperado");
            }
        } else {
            throw new CpfInvalidException("CPF inválido");
        }
    }

    private ResponseEntity<ValidationCpfDTO> getCpfValidation(final Vote vote) {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlCpfValidator.concat("/").concat(vote.getCpf()), HttpMethod.GET, entity,
                ValidationCpfDTO.class);
    }

    public List<Vote> findAll() {
        return votoRepository.findAll();
    }

    public void delete(Integer id) {
        Optional<Vote> votoById = votoRepository.findById(id);
        if (!votoById.isPresent()) {
            throw new EntityNotFoundException(VOTO_NAO_ENCONTRADO + id);
        }
        votoRepository.delete(votoById.get());
    }

    public List<Vote> findVotosByPautaId(Integer id) {
        Optional<List<Vote>> findByPautaId = votoRepository.findByPautaId(id);

        if (!findByPautaId.isPresent()) {
            throw new EntityNotFoundException(VOTO_NAO_ENCONTRADO + id);
        }

        return findByPautaId.get();
    }

}