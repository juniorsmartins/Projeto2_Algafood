package io.algafoodapi;

import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.service.CozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CrudCozinhaIntegrationTests {

	@Autowired
	private CozinhaService cozinhaService;

	@Test
	public void deveCadastrarCozinhaComSucesso() {

		// Cenário
		var novaCozinha = Cozinha.builder()
				.nome("Holandesa")
				.build();

		// Ação
		novaCozinha = this.cozinhaService.criar(novaCozinha);

		// Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void deveFalharAoCadastrarCozinhaSemNome() {
		var novaCozinha = Cozinha.builder()
				.nome(null)
				.build();

		var erroEsperado =
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
					this.cozinhaService.criar(novaCozinha);
				});

		assertThat(erroEsperado).isNotNull();
	}
}

