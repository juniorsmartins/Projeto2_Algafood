package io.algafoodapi;

import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.repository.CozinhaRepository;
import io.algafoodapi.util.CriadorDeJsons;
import io.algafoodapi.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"/application-test.properties"})
class CrudCozinhaAPITests {

    @LocalServerPort
    private int porta;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinha1;
    private int totalCozinhas;
    private int cozinhaIdInexistente = new Random().nextInt(1000) * 10000;
    private final String cozinhaBrasileira = "Brasileira";
    private final String cozinhaAmericana = "Americana";

//    @Autowired
//    private Flyway flyway;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Habilita logs pra quando testes falharem
        RestAssured.port = porta;
        RestAssured.basePath = "/v1/cozinhas";

//        flyway.migrate(); // Roda o Flyway no banco antes de cada teste. Assim cada teste pega o banco/dados iguais.

        this.databaseCleaner.clearTables();
        this.criarDadosNoBancoParaTeste();
    }

    @Test
    void deveRetornarStatus200_quandoConsultarCozinhaPorId() {
        RestAssured
            .given()
                .pathParam("cozinhaId", cozinha1.getId())
                .accept(ContentType.JSON)
            .when()
                .get("/{cozinhaId}")
            .then()
                .statusCode(200)
                .body("gastronomia", Matchers.equalTo(cozinha1.getNome()));
    }

    @Test
    void deveRetornarStatus404_quandoConsultarCozinhaPorIdInexistente() {
        RestAssured
            .given()
                .pathParam("cozinhaId", cozinhaIdInexistente)
                .accept(ContentType.JSON)
            .when()
                .get("/{cozinhaId}")
            .then()
                .statusCode(404);
    }

    @Test
    void deveConterNumeroCorretoDeCozinhas_quandoListarCozinhas() {
        RestAssured
            .given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(200)
                .body("", Matchers.hasSize(totalCozinhas))
                .body("gastronomia", Matchers.hasItems(cozinhaBrasileira, cozinhaAmericana));
    }

    @Test
    public void deveRetornarStatus201_quandoCadastrarCozinha() {
        RestAssured
            .given()
//                .body("{ \"gastronomia\": \"Síria\" }")
                .body(CriadorDeJsons.jsonDeCozinha("Síria"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private void criarDadosNoBancoParaTeste() {
        cozinha1 = Cozinha.builder()
                .nome(cozinhaBrasileira)
                .build();

        var cozinha2 = Cozinha.builder()
                .nome(cozinhaAmericana)
                .build();

        cozinha1 = this.cozinhaRepository.save(cozinha1);
        this.cozinhaRepository.save(cozinha2);

        totalCozinhas = this.cozinhaRepository.findAll().size();
    }
}

