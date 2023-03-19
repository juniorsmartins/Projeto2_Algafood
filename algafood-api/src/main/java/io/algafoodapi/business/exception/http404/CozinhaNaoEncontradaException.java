package io.algafoodapi.business.exception.http404;

public final class CozinhaNaoEncontradaException extends RecursoNaoEncontradoException {
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(String.format("Não encontrada cozinha com código %d.", id));
    }
}

