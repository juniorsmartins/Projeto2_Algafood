package io.algafoodapi.business.exception.http404;

public abstract class RecursoNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

