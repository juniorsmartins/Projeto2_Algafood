package io.algafoodapi.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.model.Endereco;
import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.model.Pedido;
import io.algafoodapi.domain.model.Produto;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestauranteDTOOut
    (
        Long id,
        String nome,
        BigDecimal taxaFrete,
        Cozinha cozinha,
        List<Produto> produtos,
        List<FormaPagamento> formasPagamento,
        List<Pedido> pedidos,
        Endereco endereco
    )
{ }

