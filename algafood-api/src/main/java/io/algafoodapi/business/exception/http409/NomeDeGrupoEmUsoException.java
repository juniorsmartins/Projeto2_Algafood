package io.algafoodapi.business.exception.http409;

import io.algafoodapi.business.core.Constantes;

public final class NomeDeGrupoEmUsoException extends RegraDeNegocioVioladaException {

    private static final long serialVersionUID = 1L;

    public NomeDeGrupoEmUsoException(String nome) {
        super(String.format(Constantes.NOME_DE_GRUPO_JA_CADASTRADO, nome));
    }
}

