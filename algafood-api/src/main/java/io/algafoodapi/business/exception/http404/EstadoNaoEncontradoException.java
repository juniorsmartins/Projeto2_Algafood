package io.algafoodapi.business.exception.http404;

public final class EstadoNaoEncontradoException extends RecursoNaoEncontradoException {
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long id) {
        this(String.format("Não encontrado estado com código %d.", id));
    }
}

