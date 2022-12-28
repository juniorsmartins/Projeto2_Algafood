package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> listar() {
        return this.manager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        return this.manager.find(Estado.class, id);
    }

    @Transactional
    @Override
    public Estado salvar(Estado estado) {
        return this.manager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        var estado = this.buscar(id);

        if(estado == null)
            throw new EmptyResultDataAccessException(1);

        this.manager.remove(estado);
    }
}
