package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.PoliticaDtoRequest;
import io.algafoodapi.presentation.dto.request.ProdutoDtoRequest;
import io.algafoodapi.presentation.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.presentation.dto.response.PoliticaDtoResponse;
import io.algafoodapi.presentation.dto.response.ProdutoDtoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PoliticaRestauranteController<R extends PoliticaDtoRequest, S extends PoliticaDtoResponse<I>, I> {

  @PatchMapping(path = "/{id}")
  ResponseEntity<S> atualizarParcial(I id, Map<String, Object> campos, HttpServletRequest request);

  @GetMapping(path = "/{id}")
  ResponseEntity<S> consultarPorId(I id);

  @GetMapping(path = "/por-nome")
  ResponseEntity<List<S>> consultarPorNome(String nome);

  @GetMapping(path = "/listar")
  ResponseEntity<List<S>> listar();

  @GetMapping(path = "/listar", params = "projecao=resumo")
  ResponseEntity<List<S>> listarResumido();

  @GetMapping(path = "/listar", params = "projecao=apenas-nome")
  ResponseEntity<List<S>> listarApenasNome();

//  @GetMapping(path = "/por-nome-e-taxas")
//  ResponseEntity<List<S>> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal);

  @PutMapping(path = "/{id}/ativos")
  ResponseEntity ativar(I id);

  @DeleteMapping(path = "/{id}/ativos")
  ResponseEntity inativar(I id);

  @PutMapping(path = "/{id}/abertura")
  ResponseEntity abertura(I id);

  @PutMapping(path = "/{id}/fechamento")
  ResponseEntity fechamento(I id);

  @GetMapping(path = "/{id}/formas-pagamento")
  ResponseEntity<Set<FormaPagamentoDtoResponse>> consultarFormasDePagamentoPorIdDeRestaurante(I id);

  @DeleteMapping(path = "/{idRestaurante}/formas-pagamento/{idFormaPagamento}")
  ResponseEntity desassociarFormaPagamentoDoRestaurantePorIds(I idRestaurante, I idFormaPagamento);

  @PutMapping(path = "/{idRestaurante}/formas-pagamento/{idFormaPagamento}")
  ResponseEntity<S> associarFormaPagamentoNoRestaurantePorIds(I idRestaurante, I idFormaPagamento);

  @PostMapping(path = "/{id}/produtos")
  ResponseEntity<ProdutoDtoResponse> cadastrarProdutoPorRestaurante(I id, ProdutoDtoRequest dtoRequest);

  @GetMapping(path = "/{id}/produtos")
  ResponseEntity<List<ProdutoDtoResponse>> consultarProdutosPorIdDeRestaurante(I id);

  @GetMapping(path = "/{idRestaurante}/produtos/{idProduto}")
  ResponseEntity<ProdutoDtoResponse> buscarProdutoPorIdNoRestaurantePorId(I idRestaurante, I idProduto);
}

