package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.repository.FormaPagamentoRepository;
import io.algafoodapi.infraestrutura.repository.jpa.FormaPagamentoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private FormaPagamentoRepositoryJpa formaPagamentoRepositoryJpa;

    @Override
    public List<FormaPagamento> listar() {
        return this.manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
    }

    @Override
    public Optional<FormaPagamento> buscar(Long id) {
        return this.formaPagamentoRepositoryJpa.findById(id);
    }

    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return this.formaPagamentoRepositoryJpa.save(formaPagamento);
    }

    @Transactional
    @Override
    public void remover(FormaPagamento formaPagamento) {
        this.formaPagamentoRepositoryJpa.delete(formaPagamento);
    }
}

