package io.algafoodapi.domain.exception.http409;

public abstract class RegraDeNegocioVioladaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RegraDeNegocioVioladaException(String mensagem) {
        super(mensagem);
    }
}
