package io.algafoodapi.api.controller;

//@SpringBootTest
//class CrudCozinhaIntegrationTests {
//
//	@Autowired
//	private CozinhaService cozinhaService;
//	@Autowired
//	private CozinhaRepository cozinhaRepository;
//	@Autowired
//	private RestauranteRepository restauranteRepository;
//	@Autowired
//	private EstadoRepository estadoRepository;
//	@Autowired
//	private CidadeRepository cidadeRepository;
//
//	@Test
//	@DisplayName("Post - Criar cozinha com sucesso")
//	public void deveAtribuirId_quandoCadastrarCozinhaComSucesso() {
//
//		// Cenário
//		var novaCozinha = Cozinha.builder()
//				.nome("Holandesa")
//				.build();
//
//		// Ação
//		novaCozinha = this.cozinhaService.criar(novaCozinha);
//
//		// Validação
//		assertThat(novaCozinha).isNotNull();
//		assertThat(novaCozinha.getId()).isNotNull();
//	}
//
//	@Test
//	@DisplayName("Post - Lançar exception ao cadastrar cozinha sem nome")
//	public void deveLancarException_quandoCadastrarCozinhaSemNome() {
//		var novaCozinha = Cozinha.builder()
//				.nome(null)
//				.build();
//
//		var erroEsperado = Assertions
//				.assertThrows(ConstraintViolationException.class, () -> {
//					this.cozinhaService.criar(novaCozinha);
//				});
//
//		assertThat(erroEsperado).isNotNull();
//	}
//
//	@Test
//	@DisplayName("Delete - Lançar exception ao excluir cozinha não encontrado")
//	void deveLancarException_quandoExcluirCozinhaNaoEncontradaPorId() {
//		var cozinhaId = 555L;
//
//		var erroEsperado = Assertions
//				.assertThrows(CozinhaNaoEncontradaException.class, () -> {
//					this.cozinhaService.excluirPorId(cozinhaId);
//				});
//
//		assertThat(erroEsperado).isNotNull();
//	}
//
//	@Test
//	@DisplayName("Delete - Lançar Exception ao excluir cozinha em uso")
//	void deveLancarException_quandoExcluirCozinhaEmUso() {
//		var cozinhaId = 1L;
//
//		var erroEsperado = Assertions
//				.assertThrows(CozinhaEmUsoException.class, () -> {
//					this.cozinhaService.excluirPorId(cozinhaId);
//				});
//
//		assertThat(erroEsperado).isNotNull();
//	}
//}

