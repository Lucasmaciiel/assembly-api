package com.lmg.desafiojavaspringboot;

import com.lmg.desafiojavaspringboot.api.model.Pauta;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class PautaAPITest {

    public static final int PAUTA_ID_INEXISTENTE = 999;
    public static final int PAUTA_ID_EXISTENTE = 1;
    public static final String PAUTA_NOME = "Nova Pauta";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/assembleia-api/pauta";
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarPautas(){
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarPautaCorretamente(){
        RestAssured.given()
                    .body("{ \"name\": \"Nova Pauta\"}")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarPautaExistente(){
        RestAssured.given()
                    .pathParam("pautaId", PAUTA_ID_EXISTENTE)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{pautaId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", Matchers.equalTo(PAUTA_NOME));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarPautaInexistente(){
        RestAssured.given()
                .pathParam("pautaId", PAUTA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{pautaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    public void prepararDados(){
        Pauta.builder()
                .name("Nova Pauta")
                .build();
    }
}
