package io.algafoodapi.business.exception.http409;

import io.algafoodapi.business.core.Constantes;

public final class NomeDeUsuarioEmUsoException extends RegraDeNegocioVioladaException {

    private static final long serialVersionUID = 1L;

    public NomeDeUsuarioEmUsoException(String nome) {
        super(String.format(Constantes.NOME_DE_USUARIO_JA_CADASTRADO, nome));
    }
}

