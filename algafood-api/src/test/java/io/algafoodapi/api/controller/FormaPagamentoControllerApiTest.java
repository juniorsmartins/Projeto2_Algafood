package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.domain.repository.FormaPagamentoRepository;
import io.algafoodapi.util.CriadorDeBuilders;
import io.algafoodapi.util.CriadorDeJsons;
import io.algafoodapi.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"/application-test.properties"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FormaPagamentoControllerApiTest {

    @LocalServerPort
    private int porta;
    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @BeforeEach
    void criadorDeCenarios() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Habilita logs pra quando testes falharem
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = porta;
        RestAssured.basePath = "/v1/formas-pagamento";
//        RestAssured.authentication = basic("username", "password");

        this.databaseCleaner.clearTables();
    }

    @Test
    @Order(1)
    @DisplayName("Criar - Fluxo Principal I - caminho feliz")
    void dadoUmJsonDeFormaPagamentoDtoRequest_quandoCadastrarCorreto_esperoResponseEntityComFormaPagamentoDtoResponseNoBodyAndHttp201() {
        var descricao = "Caderneta";

        var response = RestAssured
            .given()
                .body(CriadorDeJsons.jsonDeFormaPagamento(descricao))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(201)
                .body("descricao", equalTo(descricao.toUpperCase()))
            .extract()
                .response();

        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(FormaPagamentoDtoResponse.class, response.body().getClass());
    }


    @Test
    @Order(5)
    @DisplayName("Listar - Fluxo Principal I - caminho feliz")
    void dadoUmaRequisicaoGet_quandoListar_esperoListaComDuasFormasDePagamentoDtoResponseNoBodyAndHttp200() {
        var formaPgto1 = CriadorDeBuilders.geraFormaPagamentoBuilder()
                .build();
        var formaPgto2 = CriadorDeBuilders.geraFormaPagamentoBuilder()
                .build();
        this.formaPagamentoRepository.salvar(formaPgto1);
        this.formaPagamentoRepository.salvar(formaPgto2);

        RestAssured
            .given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(200)
                .body("size()", equalTo(2),
                        "descricao", hasItems(formaPgto1.getDescricao(), formaPgto2.getDescricao()));
    }


}

