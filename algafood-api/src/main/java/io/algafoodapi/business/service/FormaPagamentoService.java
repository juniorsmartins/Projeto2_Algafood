package io.algafoodapi.business.service;

import io.algafoodapi.business.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoService {

    FormaPagamento cadastrar(FormaPagamento formaPagamento);

    FormaPagamento atualizar(Long id, FormaPagamento formaPagamento);

    List<FormaPagamento> listar();

    void deletarPorId(Long id);
}

