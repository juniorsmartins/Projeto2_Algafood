package io.algafoodapi.business.exception.http404;

import io.algafoodapi.business.core.Constantes;

public final class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(String.format(Constantes.USUARIO_NAO_ENCONTRADO, id));
    }
}

