package com.lmg.assembly;

import com.lmg.assembly.infrastructure.model.Pauta;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@DisplayName("Teste da camada PautaController")
class PautaControllerTest {

    public static final int PAUTA_ID_INEXISTENTE = 999;
    public static final int PAUTA_ID_EXISTENTE = 1;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/assembleia-api/pauta";
        prepararDados();
    }

    @Test
    @DisplayName("Deve retornar status 200 quando consultar pautas")
    void deveRetornarStatus200_QuandoConsultarPautas() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve retornar status 201 quando cadastrar pauta corretamente")
    void deveRetornarStatus201_QuandoCadastrarPautaCorretamente() {
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
    @DisplayName("Deve retornar resposta e status corretos quando consultar pauta existente")
    void deveRetornarRespostaEStatusCorretos_QuandoConsultarPautaExistente() {
        RestAssured.given()
                .pathParam("id", PAUTA_ID_EXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo("Nova Pauta"));
    }

    @Test
    @DisplayName("Deve retornar status 404 corretos quando consultar pauta inexistente")
    void deveRetornarStatus404_QuandoConsultarPautaInexistente() {
        RestAssured.given()
                .pathParam("id", PAUTA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    void prepararDados() {
        Pauta.builder()
                .name("Nova Pauta")
                .build();
    }
}
