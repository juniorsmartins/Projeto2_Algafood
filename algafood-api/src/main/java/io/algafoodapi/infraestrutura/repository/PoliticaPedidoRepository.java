package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.presentation.filtros.PedidoFiltro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PoliticaPedidoRepository {

  Page<Pedido> pesquisar(PedidoFiltro pedidoFiltro, Pageable paginacao);

  List<Pedido> buscarTodos();
}

