package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.http404.FormaPagamentoNaoEncontradaException;
import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoServiceImpl(final FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public FormaPagamento cadastrar(FormaPagamento formaPagamento) {

        formaPagamento = this.padronizarDescricao(formaPagamento);

        return this.formaPagamentoRepository.salvar(formaPagamento);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public FormaPagamento atualizar(final Long id, final FormaPagamento formaPagamento) {

        return this.formaPagamentoRepository.buscar(id)
            .map(formaDoDatabase -> {
                BeanUtils.copyProperties(formaPagamento, formaDoDatabase, "id");
                return formaDoDatabase;
            })
            .map(this::padronizarDescricao)
            .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Override
    public List<FormaPagamento> listar() {
        return this.formaPagamentoRepository.listar();
    }

    private FormaPagamento padronizarDescricao(FormaPagamento formaPagamento) {
        var descricaoParaPadronizar = formaPagamento.getDescricao();
        var descricaoPadronizada = descricaoParaPadronizar.toUpperCase();
        formaPagamento.setDescricao(descricaoPadronizada);
        return formaPagamento;
    }
}

