package io.algafoodapi.business.service;

import io.algafoodapi.business.exception.http404.PedidoNaoEncontradoException;
import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.business.utils.ServiceUtils;
import io.algafoodapi.infraestrutura.repository.PoliticaCrudBaseRepository;
import io.algafoodapi.infraestrutura.repository.PoliticaPedidoRepository;
import io.algafoodapi.presentation.mapper.PedidoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PoliticaCrudBaseService<Pedido, Long>, PoliticaPedidoService {

  @Autowired
  private PoliticaCrudBaseRepository<Pedido, Long> pedidoCrudRepository;

  @Autowired
  private PoliticaPedidoRepository pedidoRepository;

  @Autowired
  private PedidoMapper mapper;

  @Autowired
  private ServiceUtils serviceUtils;

  @Transactional(readOnly = true)
  @Override
  public List<Pedido> listar() {

    return this.pedidoRepository.buscarTodos();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  @Override
  public Pedido cadastrar(Pedido pedido) {

    return Optional.of(pedido)
        .map(this.pedidoCrudRepository::salvar)
        .orElseThrow();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  @Override
  public Pedido atualizar(Pedido entidade) {
    return null;
  }

  @Transactional(readOnly = true)
  @Override
  public Page<Pedido> pesquisar(final Pedido entidade, final Pageable paginacao) {

    var condicoesDePesquisa = this.serviceUtils.criarCondicoesDePesquisa(entidade);
    return this.pedidoCrudRepository.pesquisar(condicoesDePesquisa, paginacao);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  @Override
  public void deletar(final Long id) {

    this.pedidoCrudRepository.consultarPorId(id)
        .map(pedido -> {
          this.pedidoCrudRepository.deletar(pedido);
          return true;
        })
        .orElseThrow(() -> new PedidoNaoEncontradoException(id));
  }
}

