package io.algafoodapi.business.service;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.presentation.filtros.PedidoFiltro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PoliticaPedidoService {

  @GetMapping(path = "/filtro")
  Page<Pedido> pesquisar(PedidoFiltro pedidoFiltro, Pageable paginacao);

  List<Pedido> listar();
}

