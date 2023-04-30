package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.infraestrutura.repository.jpa.PedidoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepositoryImpl implements PoliticaCrudBaseRepository<Pedido, Long>, PoliticaPedidoRepository {

  @Autowired
  private PedidoRepositoryJpa repositoryJpa;


  @Override
  public List<Pedido> buscarTodos() {

    return this.repositoryJpa.findAll();
  }

  @Override
  public Pedido salvar(Pedido pedido) {
    return this.repositoryJpa.save(pedido);
  }

  @Override
  public Page<Pedido> pesquisar(final Example<Pedido> example, final Pageable paginacao) {
    return this.repositoryJpa.findAll(example, paginacao);
  }

  @Override
  public void deletar(Pedido entidade) {

  }

  @Override
  public Optional<Pedido> consultarPorId(Long id) {
    return Optional.empty();
  }

  @Override
  public List<Pedido> listar() {
    return null;
  }
}

