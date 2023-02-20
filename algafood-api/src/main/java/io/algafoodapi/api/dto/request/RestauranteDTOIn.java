package io.algafoodapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.algafoodapi.domain.core.validation.FreteGratisObrigaDescricaoNoNomeAnotation;
import io.algafoodapi.domain.core.validation.GruposValid;
import io.algafoodapi.domain.core.validation.MultiploAnotation;
import io.algafoodapi.domain.core.validation.TaxaFreteAnotation;
import io.algafoodapi.domain.model.Endereco;
import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.model.Pedido;
import io.algafoodapi.domain.model.Produto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@FreteGratisObrigaDescricaoNoNomeAnotation(valorTaxaFrete = "taxaFrete", descricaoNome = "nome", descricaoObrigatoria = "Frete Grátis")
public record RestauranteDTOIn
    (
        Long id,

        @NotBlank
        String nome,

        @TaxaFreteAnotation
        @MultiploAnotation(numero = 5)
        BigDecimal taxaFrete,

        @CreationTimestamp
        OffsetDateTime dataHoraUTCCadastro,

        @UpdateTimestamp
        OffsetDateTime dataHoraUTCAtualizacao,

        @Valid
        @ConvertGroup(from = Default.class, to = GruposValid.CozinhaId.class)
        @NotNull
        CozinhaDTOIn cozinha,

        @JsonIgnore
        List<Produto> produtos,

        @JsonIgnore
        List<FormaPagamento> formasPagamento,

        @JsonIgnore
        List<Pedido> pedidos,

        Endereco endereco
    )
{ }
