package io.algafoodapi.domain.exception.http409;

import io.algafoodapi.domain.core.Constantes;

public final class EmailDeUsuarioEmUsoException extends RegraDeNegocioVioladaException {

    private static final long serialVersionUID = 1L;

    public EmailDeUsuarioEmUsoException(String email) {
        super(String.format(Constantes.EMAIL_DE_USUARIO_JA_CADASTRADO, email));
    }
}

