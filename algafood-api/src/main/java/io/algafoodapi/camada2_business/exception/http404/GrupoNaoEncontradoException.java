package io.algafoodapi.camada2_business.exception.http404;

public final class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format("Não encontrado grupo com código %d.", id));
    }
}

