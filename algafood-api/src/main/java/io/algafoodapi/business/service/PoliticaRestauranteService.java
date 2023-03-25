package io.algafoodapi.business.service;

import io.algafoodapi.business.model.FormaPagamento;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PoliticaRestauranteService<R, I> {

  R atualizarParcial(I id, Map<String, Object> campos, HttpServletRequest request);

  R consultarPorId(I id);

  List<R> consultarPorNome(String nome);

//  List<Restaurante> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal);

  List<R> listar();

  void ativar(I id);

  void inativar(I id);

  List<FormaPagamento> consultarFormasDePagamentoPorRestaurante(I id);
}

