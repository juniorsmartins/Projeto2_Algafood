package io.algafoodapi.business.exception.http404;

import io.algafoodapi.business.core.Constantes;

public final class CidadeNaoEncontradaException extends RecursoNaoEncontradoException {

    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String codigoCidade) {
        super(String.format(Constantes.CIDADE_NAO_ENCONTRADA, codigoCidade));
    }
}

