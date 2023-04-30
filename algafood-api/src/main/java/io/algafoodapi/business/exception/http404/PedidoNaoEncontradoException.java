package io.algafoodapi.business.exception.http404;

import io.algafoodapi.business.exception.ConstantesDeErro;
import org.springframework.beans.factory.annotation.Autowired;

public final class PedidoNaoEncontradoException extends RecursoNaoEncontradoException {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ConstantesDeErro constantesDeErro;

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(final Long id) {
        this(String.format("Não encontrado Pedido com código %d.", id));
    }

    public PedidoNaoEncontradoException() {
        this("Não encontrado cadastro de Pedido.");
    }
}

