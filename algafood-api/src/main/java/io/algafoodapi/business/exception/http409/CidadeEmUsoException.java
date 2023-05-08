package io.algafoodapi.business.exception.http409;

import io.algafoodapi.business.core.Constantes;

public final class CidadeEmUsoException extends RegraDeNegocioVioladaException {

    private static final long serialVersionUID = 1L;

    public CidadeEmUsoException(String codigoCidade) {
        super(String.format(Constantes.CIDADE_NAO_ENCONTRADA, codigoCidade));
    }
}

