package com.lmg.assembly;

import com.lmg.assembly.infrastructure.model.Pauta;
import com.lmg.assembly.infrastructure.model.Session;
import com.lmg.assembly.infrastructure.repository.PautaRepository;
import com.lmg.assembly.infrastructure.repository.SessionRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@DisplayName("Teste da camada SessaoController")
class SessaoControllerTest {

    public static final int SESSAO_ID_INEXISTENTE = 999;
    public static final int SESSAO_ID_EXISTENTE = 1;

    @LocalServerPort
    private int port;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        prepareData();
    }

    @Test
    void deveRetornarStatus200_QuandoConsultarSessoes() {
        given()
                .basePath("/assembleia-api/sessao/pautas/sessoes")
                .accept(ContentType.JSON)
                .when()
                .get()
                .then().statusCode(HttpStatus.OK.value());
    }


    @Test
    void deveRetornarStatus201_QuandoAbrirUmaSessaoCorretamente() {

        given()
                .basePath("/assembleia-api/sessao/pautas/1/sessoes")
                .body("{\n" +
                        "  \"sessionExpirationMinutes\": 10,\n" +
                        "  \"startDate\": \"2022-05-02T21:42:54.929Z\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarRespostaEStatusCorretos_QuandoConsultarSessaoExistente() {
        given()
                .basePath("/assembleia-api/sessao/pautas/1/sessoes")
                .pathParam("sessaoId", SESSAO_ID_EXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{sessaoId}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus400_QuandoConsultarSessaoInexistente() {
        given()
                .basePath("/assembleia-api/sessao/pautas/1/sessoes")
                .pathParam("sessaoId", SESSAO_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{sessaoId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    private void prepareData() {
        var pauta = new Pauta(1, "Nova Pauta");
        pautaRepository.save(pauta);

        var session = Session.builder()
                .id(1)
                .startDate(LocalDateTime.now())
                .sessionExpirationMinutes(10)
                .pauta(pauta)
                .build();

        sessionRepository.save(session);
    }

}
