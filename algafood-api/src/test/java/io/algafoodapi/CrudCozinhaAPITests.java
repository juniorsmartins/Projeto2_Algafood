package io.algafoodapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudCozinhaAPITests {

    @LocalServerPort
    private int porta;

    @Test
    void deveRetornarStatus200_quandoConsultarCozinhas() {
        RestAssured
                .given()
                    .basePath("/v1/cozinhas")
                    .port(porta)
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(200);
    }
}

