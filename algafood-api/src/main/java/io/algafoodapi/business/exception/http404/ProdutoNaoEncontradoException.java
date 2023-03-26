package io.algafoodapi.business.exception.http404;

import io.algafoodapi.business.exception.ConstantesDeErro;
import org.springframework.beans.factory.annotation.Autowired;

public final class ProdutoNaoEncontradoException extends RecursoNaoEncontradoException {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ConstantesDeErro constantesDeErro;

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(final Long id) {
        this(String.format("Não encontrado Produto com código %d.", id));
    }

    public ProdutoNaoEncontradoException() {
        this("Não encontrado cadastro de Produto.");
    }
}

