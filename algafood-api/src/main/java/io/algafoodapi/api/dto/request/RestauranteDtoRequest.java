package io.algafoodapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.algafoodapi.domain.core.validation.FreteGratisObrigaDescricaoNoNomeAnotation;
import io.algafoodapi.domain.core.validation.MultiploAnotation;
import io.algafoodapi.domain.core.validation.TaxaFreteAnotation;
import io.algafoodapi.domain.model.Endereco;
import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.model.Pedido;
import io.algafoodapi.domain.model.Produto;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Builder
@FreteGratisObrigaDescricaoNoNomeAnotation(valorTaxaFrete = "taxaFrete", descricaoNome = "nome", descricaoObrigatoria = "Frete Grátis")
public record RestauranteDtoRequest
    (
        @NotBlank
        String nome,

        @TaxaFreteAnotation
        @MultiploAnotation(numero = 5)
        BigDecimal taxaFrete,

        @NotNull
        @Valid
        CozinhaIdRequest cozinha,

        @JsonIgnore
        List<Produto> produtos,

        @JsonIgnore
        List<FormaPagamento> formasPagamento,

        @JsonIgnore
        List<Pedido> pedidos,

        @NotNull
        @Valid
        Endereco endereco
    )
{ }

