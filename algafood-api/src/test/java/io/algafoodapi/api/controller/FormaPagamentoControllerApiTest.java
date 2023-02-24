package io.algafoodapi.api.controller;

import io.algafoodapi.util.CriadorDeJsons;
import io.algafoodapi.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"/application-test.properties"})
class FormaPagamentoControllerApiTest {

    @LocalServerPort
    private int porta;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void criadorDeCenarios() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Habilita logs pra quando testes falharem
        RestAssured.port = porta;
        RestAssured.basePath = "/v1/formas-pagamento";
//        RestAssured.authentication = basic("username", "password");

        this.databaseCleaner.clearTables();
    }

    @Test
    @Order(1)
    @DisplayName("Criar - Fluxo Principal I - caminho feliz")
    void dadoUmJsonDeFormaPagamentoDtoRequest_quandoCadastrarCorreto_esperoResponseEntityComFormaPagamentoDtoResponseNoBodyAndHttp201() {
        RestAssured
                .given()
                    .body(CriadorDeJsons.jsonDeFormaPagamento("Caderneta"))
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(201);

    }


}

