package com.lmg.assembly;

import com.lmg.assembly.infrastructure.model.Pauta;
import com.lmg.assembly.infrastructure.model.Session;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class SessaoAPITest {

    public static final int SESSAO_ID_INEXISTENTE = 999;
    public static final int SESSAO_ID_EXISTENTE = 1;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
//        RestAssured.basePath = "/assembleia-api/sessao/pautas/sessoes";
        prepararDados();
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
                .basePath("/assembleia-api/sessao")
                .body("{ \n" +
                        "  \"minutesValidity\": 10,\n" +
                        "  \"pauta\": {\n" +
                        "    \"id\": 2,\n" +
                        "    \"name\": \"Nova Pauta\"\n" +
                        "  },\n" +
                        "  \"startDate\": \"2021-05-25T09:41:00.582Z\"\n" +
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
    void deveRetornarStatus404_QuandoConsultarSessaoInexistente() {
        given()
                .basePath("/assembleia-api/sessao/pautas/1/sessoes")
                .pathParam("sessaoId", SESSAO_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{sessaoId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        var pauta = new Pauta(1, "Nova Pauta");

        Session.builder()
                .id(1)
                .dataInicio(LocalDateTime.now())
                .minutosExpiracao(10)
                .pauta(pauta)
                .build();
    }

}
