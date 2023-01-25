package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.domain.model.Permissao;
import io.algafoodapi.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Transactional(readOnly = true)
    @Override
    public List<Permissao> listar() {
        return this.manager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Permissao buscar(Long id) {
        return this.manager.find(Permissao.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Override
    public Permissao salvar(Permissao permissao) {
        return this.manager.merge(permissao);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Override
    public void remover(Permissao permissao) {
        permissao = buscar(permissao.getId());
        this.manager.remove(permissao);
    }
}
