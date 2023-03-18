package io.algafoodapi.camada2_business.ports;

import io.algafoodapi.camada2_business.model.FormaPagamento;

import java.util.List;
import java.util.Optional;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();

    Optional<FormaPagamento> buscar(Long id);

    FormaPagamento salvar(FormaPagamento formaPagamento);

    void remover(FormaPagamento formaPagamento);
}
