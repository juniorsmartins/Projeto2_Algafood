package io.algafoodapi.business.exception.http400;

public final class RequisicaoMalFormuladaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RequisicaoMalFormuladaException(String mensagem) {
        super(mensagem);
    }

    public RequisicaoMalFormuladaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

