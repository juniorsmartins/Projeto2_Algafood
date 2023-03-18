package io.algafoodapi.camada3_infraestrutura.repository;

import io.algafoodapi.camada2_business.model.FormaPagamento;
import io.algafoodapi.camada2_business.ports.FormaPagamentoRepository;
import io.algafoodapi.camada3_infraestrutura.repository.jpa.FormaPagamentoRepositoryJpa;
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

