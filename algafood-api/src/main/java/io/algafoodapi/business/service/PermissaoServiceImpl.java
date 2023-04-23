package io.algafoodapi.business.service;

import io.algafoodapi.business.model.Permissao;
import io.algafoodapi.business.utils.ServiceUtils;
import io.algafoodapi.infraestrutura.repository.PoliticaCrudBaseRepository;
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

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  @Override
  public Permissao cadastrar(Permissao entidade) {

    return Optional.of(entidade)
        .map(permissao -> this.serviceUtils.regraGarantirNomeUnico(permissao, repository))
        .map(this.serviceUtils::padronizarNome)
        .map(this.repository::salvar)
        .orElseThrow();
  }

  @Override
  public Permissao atualizar(Permissao entidade) {
    return null;
  }

  @Override
  public Page<Permissao> pesquisar(Permissao entidade, Pageable paginacao) {
    return null;
  }

  @Override
  public void deletar(Long id) {

  }
}

