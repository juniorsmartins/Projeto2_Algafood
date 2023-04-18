package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Pedido;

import java.util.List;

public interface PoliticaPedidoRepository {

  List<Pedido> buscarTodos();
}

