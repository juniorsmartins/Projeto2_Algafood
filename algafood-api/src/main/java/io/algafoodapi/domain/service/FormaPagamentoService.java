package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.FormaPagamento;

public interface FormaPagamentoService {

    FormaPagamento cadastrar(FormaPagamento formaPagamento);

    FormaPagamento atualizar(Long id, FormaPagamento formaPagamento);
}

