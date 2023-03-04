package io.algafoodapi.api.controller;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource({"/application-test.properties"})
//class CrudCozinhaAPITests {
//
//    @LocalServerPort
//    private int porta;
//
//    @Autowired
//    private DatabaseCleaner databaseCleaner;
//
//    @Autowired
//    private CozinhaRepository cozinhaRepository;
//
//    private Cozinha cozinha1;
//    private int totalCozinhas;
//    private int cozinhaIdInexistente = new Random().nextInt(1000) * 10000;
//    private final String cozinhaBrasileira = "Brasileira";
//    private final String cozinhaAmericana = "Americana";
//
////    @Autowired
////    private Flyway flyway;
//
//    @BeforeEach
//    void setUp() {
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Habilita logs pra quando testes falharem
//        RestAssured.port = porta;
//        RestAssured.basePath = "/v1/cozinhas";
//
////        flyway.migrate(); // Roda o Flyway no banco antes de cada teste. Assim cada teste pega o banco/dados iguais.
//
//        this.databaseCleaner.clearTables();
//        this.criarDadosNoBancoParaTeste();
//    }
//
//    @Test
//    void deveRetornarStatus200_quandoConsultarCozinhaPorId() {
//        RestAssured
//            .given()
//                .pathParam("cozinhaId", cozinha1.getId())
//                .accept(ContentType.JSON)
//            .when()
//                .get("/{cozinhaId}")
//            .then()
//                .statusCode(200)
//                .body("gastronomia", Matchers.equalTo(cozinha1.getNome()));
//    }
//
//    @Test
//    void deveRetornarStatus404_quandoConsultarCozinhaPorIdInexistente() {
//        RestAssured
//            .given()
//                .pathParam("cozinhaId", cozinhaIdInexistente)
//                .accept(ContentType.JSON)
//            .when()
//                .get("/{cozinhaId}")
//            .then()
//                .statusCode(404);
//    }
//
//    @Test
//    void deveConterNumeroCorretoDeCozinhas_quandoListarCozinhas() {
//        RestAssured
//            .given()
//                .accept(ContentType.JSON)
//            .when()
//                .get()
//            .then()
//                .statusCode(200)
//                .body("", Matchers.hasSize(totalCozinhas))
//                .body("gastronomia", Matchers.hasItems(cozinhaBrasileira, cozinhaAmericana));
//    }
//
//    @Test
//    void deveRetornarStatus201_quandoCadastrarCozinha() {
//        RestAssured
//            .given()
////                .body("{ \"gastronomia\": \"Síria\" }")
//                .body(CriadorDeJsons.jsonDeCozinha("Síria"))
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//            .when()
//                .post()
//            .then()
//                .statusCode(HttpStatus.CREATED.value());
//    }
//
//    private void criarDadosNoBancoParaTeste() {
//        cozinha1 = Cozinha.builder()
//                .nome(cozinhaBrasileira)
//                .build();
//
//        var cozinha2 = Cozinha.builder()
//                .nome(cozinhaAmericana)
//                .build();
//
//        cozinha1 = this.cozinhaRepository.save(cozinha1);
//        this.cozinhaRepository.save(cozinha2);
//
//        totalCozinhas = this.cozinhaRepository.findAll().size();
//    }
//}

