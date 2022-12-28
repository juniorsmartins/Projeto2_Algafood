package io.algafoodapi.domain.exception;

public final class RequisicaoMalFormuladaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RequisicaoMalFormuladaException(String mensagem) {
        super(mensagem);
    }
}
