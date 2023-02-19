package io.algafoodapi.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.algafoodapi.domain.model.ItemPedido;
import io.algafoodapi.domain.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class ProdutoMixin {

    @JsonIgnoreProperties(value = {"nome", "taxaFrete", "dataCadastro", "dataAtualizacao", "cozinha", "produtos"}, allowGetters = true)
    private Restaurante restaurante;

    @JsonIgnore
    private List<ItemPedido> itensPedido = new ArrayList<>();
}
