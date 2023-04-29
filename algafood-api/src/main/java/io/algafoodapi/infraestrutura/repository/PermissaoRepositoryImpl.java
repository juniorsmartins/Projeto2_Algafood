package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Permissao;
import io.algafoodapi.business.ports.PermissaoRepository;
import io.algafoodapi.infraestrutura.repository.jpa.PermissaoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PermissaoRepositoryImpl implements PoliticaCrudBaseRepository<Permissao, Long>, PermissaoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PermissaoRepositoryJpa repositoryJpa;

    @Transactional(readOnly = true)
    @Override
    public List<Permissao> listar() {
        return this.manager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Permissao buscar(final Long id) {
        return this.manager.find(Permissao.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Override
    public Permissao salvar(Permissao permissao) {
        return this.manager.merge(permissao);
    }

    @Override
    public Page<Permissao> pesquisar(final Example<Permissao> example, final Pageable paginacao) {
        return this.repositoryJpa.findAll(example, paginacao);
    }

    @Override
    public void deletar(Permissao entidade) {
        entidade = this.buscar(entidade.getId());
        this.manager.remove(entidade);
    }

    @Override
    public Optional<Permissao> consultarPorId(final Long id) {
        return this.repositoryJpa.findById(id);
//        return Optional.of(this.manager.find(Permissao.class, id));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Override
    public void remover(Permissao permissao) {
        permissao = this.buscar(permissao.getId());
        this.manager.remove(permissao);
    }
}
