package io.algafoodapi.business.service;

import io.algafoodapi.business.model.FormaPagamento;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PoliticaRestauranteService<Restaurante, I> {

  Restaurante atualizarParcial(I id, Map<String, Object> campos, HttpServletRequest request);

  Restaurante consultarPorId(I id);

  List<Restaurante> consultarPorNome(String nome);

//  List<Restaurante> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal);

  List<Restaurante> listar();

  void ativar(I id);

  void inativar(I id);

  List<FormaPagamento> consultarFormasDePagamentoPorRestaurante(I id);
}

