package io.algafoodapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudCozinhaAPITests {

    @LocalServerPort
    private int porta;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Habilita logs pra quando testes falharem
        RestAssured.port = porta;
    }

    @Test
    void deveRetornarStatus200_quandoConsultarCozinhas() {
        RestAssured
                .given()
                    .basePath("/v1/cozinhas")
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(200);
    }

    @Test
    void deveConterQuatroCozinhas_quandoListarCozinhas() {
        RestAssured
                .given()
                    .basePath("/v1/cozinhas")
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", Matchers.hasSize(4));
    }
}

