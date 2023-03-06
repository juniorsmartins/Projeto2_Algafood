package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.ports.FormaPagamentoRepository;
import io.algafoodapi.infraestrutura.repository.jpa.FormaPagamentoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @Autowired
    private FormaPagamentoRepositoryJpa formaPagamentoRepositoryJpa;

    @Override
    public List<FormaPagamento> listar() {
        return this.formaPagamentoRepositoryJpa.findAll();
    }

    @Override
    public Optional<FormaPagamento> buscar(Long id) {
        return this.formaPagamentoRepositoryJpa.findById(id);
    }

    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return this.formaPagamentoRepositoryJpa.save(formaPagamento);
    }

    @Override
    public void remover(FormaPagamento formaPagamento) {
        this.formaPagamentoRepositoryJpa.delete(formaPagamento);
    }
}

