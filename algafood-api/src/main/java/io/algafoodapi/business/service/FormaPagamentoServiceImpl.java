package io.algafoodapi.business.service;

import io.algafoodapi.business.exception.http404.FormaPagamentoNaoEncontradaException;
import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.business.ports.FormaPagamentoRepository;
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

    @Transactional(readOnly = true)
    @Override
    public List<FormaPagamento> listar() {
        return this.formaPagamentoRepository.listar();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void deletarPorId(final Long id) {
        this.formaPagamentoRepository.buscar(id)
            .map(formaPagamento -> {
                this.formaPagamentoRepository.remover(formaPagamento);
                return true;
            })
            .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    private FormaPagamento padronizarDescricao(FormaPagamento formaPagamento) {
        var descricaoParaPadronizar = formaPagamento.getNome();
        var descricaoPadronizada = descricaoParaPadronizar.toUpperCase();
        formaPagamento.setNome(descricaoPadronizada);
        return formaPagamento;
    }
}
