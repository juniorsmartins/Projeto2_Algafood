package io.algafoodapi.api.controller;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource({"/application-test.properties"})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class FormaPagamentoControllerApiTest {
//
//    @LocalServerPort
//    private int porta;
//    @Autowired
//    private DatabaseCleaner databaseCleaner;
//    @Autowired
//    private FormaPagamentoRepository formaPagamentoRepository;
//
//    @BeforeEach
//    void criadorDeCenarios() {
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Habilita logs pra quando testes falharem
////        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = porta;
//        RestAssured.basePath = "/v1/formas-pagamento";
////        RestAssured.authentication = basic("username", "password");
//
//        this.databaseCleaner.clearTables();
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("Criar - Fluxo Principal I - caminho feliz")
//    void dadoUmJsonDeFormaPagamentoDtoRequest_quandoCadastrar_esperoResponseEntityComFormaPagamentoDtoResponseNoBodyAndHttp201() {
//        var descricao = "Caderneta";
//
//        RestAssured
//            .given()
//                .body(CriadorDeJsons.jsonDeFormaPagamento(descricao))
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//            .when()
//                .post()
//            .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .body("descricao", equalTo(descricao.toUpperCase()));
////            .extract()
////                .response();
//
////        Assertions.assertEquals(ResponseEntity.class, response.getClass());
////        Assertions.assertEquals(FormaPagamentoDtoResponse.class, response.body().getClass());
//    }
//
////    @Test
////    @Order(5)
////    @DisplayName("Listar - Fluxo Principal I - caminho feliz")
////    void dadoUmaRequisicaoGet_quandoListar_esperoListaComDuasFormasDePagamentoDtoResponseNoBodyAndHttp200() {
////        var formaPgto1 = CriadorDeBuilders.geraFormaPagamentoBuilder()
////                .build();
////        var formaPgto2 = CriadorDeBuilders.geraFormaPagamentoBuilder()
////                .build();
////        this.formaPagamentoRepository.salvar(formaPgto1);
////        this.formaPagamentoRepository.salvar(formaPgto2);
////
////        RestAssured
////            .given()
////                .accept(ContentType.JSON)
////            .when()
////                .get()
////            .then()
////                .statusCode(200)
////                .body("size()", equalTo(2),
////                        "descricao", hasItems(formaPgto1.getDescricao(), formaPgto2.getDescricao()));
////    }
//
//
//}

