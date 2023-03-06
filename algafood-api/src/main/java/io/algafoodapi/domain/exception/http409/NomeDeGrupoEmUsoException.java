package io.algafoodapi.domain.exception.http409;

public final class NomeDeGrupoEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = 1L;

    public NomeDeGrupoEmUsoException(String mensagem) {
        super(mensagem);
    }
}

