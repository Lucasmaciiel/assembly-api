package com.lmg.desafiojavaspringboot.api.service;

import com.lmg.desafiojavaspringboot.api.dto.ValidacaoCpfDTO;
import com.lmg.desafiojavaspringboot.api.exception.*;
import com.lmg.desafiojavaspringboot.api.model.Sessao;
import com.lmg.desafiojavaspringboot.api.model.Voto;
import com.lmg.desafiojavaspringboot.api.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    private static final String CPF_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

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

    public Voto findById(Integer id) {
        Optional<Voto> findById = votoRepository.findById(id);
        if (!findById.isPresent()) {
            throw new EntidadeNaoEncontradaException("Voto não encontrado com o ID: " + id);
        }
        return findById.get();
    }

    public Voto createVoto(Integer idPauta, Integer idSessao, Voto voto) {
        var sessao = sessaoService.findByIdAndPautaId(idSessao, idPauta);
        if (!idPauta.equals(sessao.getPauta().getId())) {
            throw new SessaoInvalidaException("Sessão inválida");
        }
        voto.setPauta(sessao.getPauta());
        return verifyAndSave(sessao, voto);
    }

    private Voto verifyAndSave(final Sessao sessao, final Voto voto) {
        verifyVoto(sessao, voto);
        return votoRepository.save(voto);
    }

    protected void verifyVoto(final Sessao sessao, final Voto voto) {

        LocalDateTime dataLimite = sessao.getDataInicio().plusMinutes(sessao.getMinutosExpiracao());
        if (LocalDateTime.now().isAfter(dataLimite)) {
            throw new SessaoExpiradaException("Tempo de sessão expirada");
        }

        cpfAbleToVote(voto);
        votoAlreadyExists(voto);
    }

    protected void votoAlreadyExists(final Voto voto) {
        Optional<Voto> votoByCpfAndPauta = votoRepository.findByCpfAndPautaId(voto.getCpf(), voto.getPauta().getId());

        if (votoByCpfAndPauta.isPresent()) {
            throw new VotoJaExisteException("Voto ja existente para o CPF: " + voto.getCpf());
        }
    }

    protected void cpfAbleToVote(final Voto voto) {
        ResponseEntity<ValidacaoCpfDTO> cpfValidation = getCpfValidation(voto);
        if (HttpStatus.OK.equals(cpfValidation.getStatusCode())) {
            if (CPF_UNABLE_TO_VOTE.equalsIgnoreCase(cpfValidation.getBody().getStatus())) {
                throw new CpfNaoPodeExecutarEssaOperacaoException("Este CPF não pode executar essa operação, é necessário ser cooperado");
            }
        } else {
            throw new CpfInvalidoException("CPF inválido");
        }
    }

    private ResponseEntity<ValidacaoCpfDTO> getCpfValidation(final Voto voto) {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlCpfValidator.concat("/").concat(voto.getCpf()), HttpMethod.GET, entity,
                ValidacaoCpfDTO.class);
    }

    public List<Voto> findAll() {
        return votoRepository.findAll();
    }

    public void delete(Integer id) {
        Optional<Voto> votoById = votoRepository.findById(id);
        if (!votoById.isPresent()) {
            throw new EntidadeNaoEncontradaException("Voto não encontrado: " + id);
        }
        votoRepository.delete(votoById.get());
    }

    public List<Voto> findVotosByPautaId(Integer id) {
        Optional<List<Voto>> findByPautaId = votoRepository.findByPautaId(id);

        if (!findByPautaId.isPresent()) {
            throw new EntidadeNaoEncontradaException("Voto não encontrado: " + id);
        }

        return findByPautaId.get();
    }

}