package io.algafoodapi.presentation.dto.request;

import io.algafoodapi.business.core.validation.FreteGratisObrigaDescricaoNoNomeAnotation;
import io.algafoodapi.business.core.validation.MultiploAnotation;
import io.algafoodapi.business.core.validation.TaxaFreteAnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@FreteGratisObrigaDescricaoNoNomeAnotation(valorTaxaFrete = "taxaFrete", descricaoNome = "nome", descricaoObrigatoria = "Frete Grátis")
public final class RestauranteAtualizarDtoRequest implements PoliticaAtualizarDtoRequest<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank
    private String nome;

    @TaxaFreteAnotation
    @MultiploAnotation(numero = 5)
    private BigDecimal taxaFrete;

    @NotNull
    @Valid
    private CozinhaIdRequest cozinha;

    @NotNull
    @Valid
    private EnderecoDtoRequest endereco;
}

