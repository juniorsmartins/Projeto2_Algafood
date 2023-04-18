package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.infraestrutura.repository.jpa.PedidoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PedidoRepositoryImpl implements PoliticaPedidoRepository {

  @Autowired
  private PedidoRepositoryJpa repositoryJpa;


  @Override
  public List<Pedido> buscarTodos() {

    return this.repositoryJpa.findAll();
  }
}

