package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.FormaPagamento;
import io.algafoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoServiceImpl(final FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public FormaPagamento criar(FormaPagamento formaPagamento) {
        return this.formaPagamentoRepository.salvar(formaPagamento);
    }
}

