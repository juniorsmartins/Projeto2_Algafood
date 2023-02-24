package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoServiceImpl(final FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Override
    public FormaPagamento criar(FormaPagamento formaPagamento) {
        return this.formaPagamentoRepository.salvar(formaPagamento);
    }
}

