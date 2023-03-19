package io.algafoodapi.business.exception.http404;

public final class FormaPagamentoNaoEncontradaException extends RecursoNaoEncontradoException {
    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format("Não encontrada forma de pagamento com código %d.", id));
    }
}

