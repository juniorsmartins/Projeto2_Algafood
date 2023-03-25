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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"nome", "endereco.cep"})
@FreteGratisObrigaDescricaoNoNomeAnotation(valorTaxaFrete = "taxaFrete", descricaoNome = "nome", descricaoObrigatoria = "Frete Grátis")
public final class RestauranteDtoRequest implements PoliticaDtoRequest, Serializable {

    private static final long serialVersionUID = 1L;

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

    @NotNull
    @Valid
    private List<FormaPagamentoIdDtoRequest> formasPagamento;
}

