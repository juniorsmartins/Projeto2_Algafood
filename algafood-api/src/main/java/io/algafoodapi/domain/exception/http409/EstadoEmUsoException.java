package io.algafoodapi.domain.exception.http409;

public final class EstadoEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = 1L;

    public EstadoEmUsoException(String mensagem) {
        super(mensagem);
    }

    public EstadoEmUsoException(Long id) {
        this(String.format("Estado, com ID %d, não pode ser apagado por estar em uso.", id));
    }
}

