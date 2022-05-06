package com.lmg.assembly;

import com.lmg.assembly.common.exception.EntityNotFoundException;
import com.lmg.assembly.domain.service.VoteService;
import com.lmg.assembly.infrastructure.model.Pauta;
import com.lmg.assembly.infrastructure.model.Session;
import com.lmg.assembly.infrastructure.model.Vote;
import com.lmg.assembly.infrastructure.repository.PautaRepository;
import com.lmg.assembly.infrastructure.repository.SessionRepository;
import com.lmg.assembly.infrastructure.repository.VoteRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@DisplayName("Teste da camada VoteService")
class VoteServiceTest {

    public static final int ID_VOTE_INEXISTENTE = 1000;
    public static final int ID_VOTE_EXISTENTE = 1;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @LocalServerPort
    private int port;

    @Transactional
    @BeforeEach
    public void setUp() {

        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        prepareData();

    }

//    @Test
//    @DisplayName("Deve buscar um voto por Id com sucesso")
//    void deveRealizarUmaBuscaPorId() {
//
//        var vote = voteService.findById(ID_VOTE_EXISTENTE);
//
//        assertThat(vote).isNotNull();
//        assertThat(vote.getId()).isEqualTo(2);
//    }

    @Test
    @DisplayName("Deve falhar ao buscar voto inexistente")
    void deveFalharAoBuscarVotoInexistente() {

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> voteService.findById(ID_VOTE_INEXISTENTE));
    }


//    @Test
//    @DisplayName("Deve falhar caso a pauta da sessão não for a sessão que estiver votando")
//    void deveFalharAoLancarVotoSeASessaoNaoPertencerAPauta(){
//
//        var vote = voteRepository.findById(1).get();
//
//        assertThatExceptionOfType(SessionInvalidException.class)
//                .isThrownBy(() -> voteService.createVoto(1, 2, vote));
//    }


    private void prepareData() {
        //Cria pauta
        var pauta = new Pauta(1, "Nova Pauta");
        pautaRepository.saveAndFlush(pauta);

        //Cria sessão
        var session = Session.builder()
                .id(1)
                .startDate(LocalDateTime.now())
                .sessionExpirationMinutes(10)
                .pauta(pauta)
                .build();

        sessionRepository.saveAndFlush(session);

        //Cria o voto
        var vote = Vote.builder()
                .choice(Boolean.TRUE)
                .cpf("417.472.330-30")
                .pauta(pauta)
                .build();

        voteRepository.saveAndFlush(vote);
    }
}
