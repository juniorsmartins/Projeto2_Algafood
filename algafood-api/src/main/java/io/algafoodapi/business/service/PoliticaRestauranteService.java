package io.algafoodapi.business.service;

import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.business.model.Produto;
import io.algafoodapi.presentation.dto.response.ProdutoDtoResponse;
import io.algafoodapi.presentation.dto.response.RestauranteDtoResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PoliticaRestauranteService<R, I> {

  R atualizarParcial(I id, Map<String, Object> campos, HttpServletRequest request);

  R consultarPorId(I id);

  List<R> consultarPorNome(String nome);

//  List<Restaurante> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal);

  List<R> listar();

  void ativar(I id);

  void inativar(I id);

  void abertura(I id);

  void fechamento(I id);

  Set<FormaPagamento> consultarFormasDePagamentoPorRestaurante(I id);

  void desassociarFormaPagamentoDoRestaurantePorIds(I idRestaurante, I idFormaPagamento);

  RestauranteDtoResponse associarFormaPagamentoNoRestaurantePorIds(I idRestaurante, I idFormaPagamento);

  Produto cadastrarProdutoPorRestaurante(I id, Produto produto);

  List<Produto> consultarProdutosPorRestaurante(I id);

  ProdutoDtoResponse buscarProdutoPorIdNoRestaurantePorId(I idRestaurante, I idProduto);

}

