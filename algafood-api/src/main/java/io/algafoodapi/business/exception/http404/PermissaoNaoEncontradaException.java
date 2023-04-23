package io.algafoodapi.business.exception.http404;

public final class PermissaoNaoEncontradaException extends RecursoNaoEncontradoException {

    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long id) {
        this(String.format("Não encontrada permissão com código %d.", id));
    }
}

