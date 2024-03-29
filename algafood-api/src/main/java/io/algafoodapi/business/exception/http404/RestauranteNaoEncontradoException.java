package io.algafoodapi.business.exception.http404;

public final class RestauranteNaoEncontradoException extends RecursoNaoEncontradoException {

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Não encontrado restaurante com código %d.", id));
    }
}

