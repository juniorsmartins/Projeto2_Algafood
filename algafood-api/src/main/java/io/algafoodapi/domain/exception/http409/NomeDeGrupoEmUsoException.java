package io.algafoodapi.domain.exception.http409;

import io.algafoodapi.domain.core.Constantes;

public final class NomeDeGrupoEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = 1L;

    public NomeDeGrupoEmUsoException(String nome) {
        super(String.format(Constantes.NOME_DE_GRUPO_JA_CADASTRADO, nome));
    }
}

