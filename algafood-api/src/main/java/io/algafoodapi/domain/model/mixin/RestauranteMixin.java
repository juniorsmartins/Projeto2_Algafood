package io.algafoodapi.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.model.Endereco;
import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.model.Pedido;
import io.algafoodapi.domain.model.Produto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMixin { // Classe para configurações do Jackson.annotation

    @JsonIgnoreProperties(value = "nome", allowGetters = true) // Ignora o nome da cozinha a partir do restaurante (apenas na deserialização). Dá para ignorar vários nomes se colocar um array de nomes
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();

    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();
}

