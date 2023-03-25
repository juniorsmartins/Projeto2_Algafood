package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.PoliticaDtoRequest;
import io.algafoodapi.presentation.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.presentation.dto.response.PoliticaDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PoliticaRestauranteController<R extends PoliticaDtoRequest, S extends PoliticaDtoResponse<I>, I> {

  @PatchMapping(path = "/{id}")
  ResponseEntity<S> atualizarParcial(I id, Map<String, Object> campos, HttpServletRequest request);

  @GetMapping(path = "/{id}")
  ResponseEntity<S> consultarPorId(I id);

  @GetMapping(path = "/por-nome")
  ResponseEntity<List<S>> consultarPorNome(String nome);

  @GetMapping(path = "/listar")
  ResponseEntity<List<S>> listar();

//  @GetMapping(path = "/por-nome-e-taxas")
//  ResponseEntity<List<S>> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal);

  @PutMapping(path = "/{id}/ativos")
  ResponseEntity ativar(I id);

  @DeleteMapping(path = "/{id}/ativos")
  ResponseEntity inativar(I id);

  @GetMapping(path = "/{id}/formas-pagamento")
  ResponseEntity<List<FormaPagamentoDtoResponse>> consultarFormasDePagamentoPorRestaurante(I id);
}

