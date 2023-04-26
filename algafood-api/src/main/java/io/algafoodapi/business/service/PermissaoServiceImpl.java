package io.algafoodapi.business.service;

import io.algafoodapi.business.exception.ConstantesDeErro;
import io.algafoodapi.business.exception.http404.PermissaoNaoEncontradaException;
import io.algafoodapi.business.model.Permissao;
import io.algafoodapi.business.utils.ServiceUtils;
import io.algafoodapi.infraestrutura.repository.PoliticaCrudBaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PermissaoServiceImpl implements PoliticaCrudBaseService<Permissao, Long> {

  @Autowired
  private PoliticaCrudBaseRepository<Permissao, Long> repository;

  @Autowired
  private ServiceUtils serviceUtils;

  @Autowired
  private ConstantesDeErro constantesDeErro;

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  @Override
  public Permissao cadastrar(Permissao entidade) {

    return Optional.of(entidade)
        .map(permissao -> this.serviceUtils.regraGarantirNomeUnico(permissao, repository))
        .map(this.serviceUtils::padronizarNome)
        .map(this.repository::salvar)
        .orElseThrow();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  @Override
  public Permissao atualizar(Permissao entidade) {
    var id = entidade.getId();

    return this.repository.consultarPorId(id)
        .map(permissao -> this.serviceUtils.regraGarantirNomeUnico(permissao, repository))
        .map(permissao -> {
          BeanUtils.copyProperties(entidade, permissao, "id");
          return permissao;
        })
        .map(this.serviceUtils::regraCapitalizarNome)
        .orElseThrow(() -> new PermissaoNaoEncontradaException(id));
  }

  @Transactional(readOnly = true)
  @Override
  public Page<Permissao> pesquisar(Permissao entidade, Pageable paginacao) {

    var condicoesDePesquisa = this.serviceUtils.criarCondicoesDePesquisa(entidade);
    var resultadoDaPesquisa = this.repository.pesquisar(condicoesDePesquisa, paginacao);

    if (resultadoDaPesquisa.isEmpty()) {
      throw new PermissaoNaoEncontradaException(constantesDeErro.NENHUM_RECURSO_ENCONTRADO);
    }

    return resultadoDaPesquisa;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  @Override
  public void deletar(final Long id) {

    this.repository.consultarPorId(id)
        .map(permissao -> {
          this.repository.deletar(permissao);
          return true;
        })
        .orElseThrow(() -> new PermissaoNaoEncontradaException(id));
  }
}

