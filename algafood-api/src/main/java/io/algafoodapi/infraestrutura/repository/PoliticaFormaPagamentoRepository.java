package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.FormaPagamento;

import java.util.List;
import java.util.Optional;

public interface PoliticaFormaPagamentoRepository {

    List<FormaPagamento> listar();

    Optional<FormaPagamento> buscar(Long id);

    FormaPagamento salvar(FormaPagamento formaPagamento);

    void remover(FormaPagamento formaPagamento);
}
