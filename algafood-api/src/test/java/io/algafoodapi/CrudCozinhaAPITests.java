package io.algafoodapi;

import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.repository.CozinhaRepository;
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
                .body("gastronomia", Matchers.equalTo("Brasileira"));
    }

    @Test
    void deveRetornarStatus404_quandoConsultarCozinhaPorIdInexistente() {
        RestAssured
            .given()
                .pathParam("cozinhaId", 50000)
                .accept(ContentType.JSON)
            .when()
                .get("/{cozinhaId}")
            .then()
                .statusCode(404);
    }

    @Test
    void deveConterDuasCozinhas_quandoListarCozinhas() {
        RestAssured
            .given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(200)
                .body("", Matchers.hasSize(2))
                .body("gastronomia", Matchers.hasItems("Brasileira", "Americana"));
    }

    @Test
    public void deveRetornarStatus201_quandoCadastrarCozinha() {
        RestAssured
            .given()
                .body("{ \"gastronomia\": \"Síria\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private void criarDadosNoBancoParaTeste() {
        cozinha1 = Cozinha.builder()
                .nome("Brasileira")
                .build();

        var cozinha2 = Cozinha.builder()
                .nome("Americana")
                .build();

        cozinha1 = this.cozinhaRepository.save(cozinha1);
        this.cozinhaRepository.save(cozinha2);
    }
}

