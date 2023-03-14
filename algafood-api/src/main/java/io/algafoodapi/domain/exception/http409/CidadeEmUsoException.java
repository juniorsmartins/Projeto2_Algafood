package io.algafoodapi.domain.exception.http409;

public final class CidadeEmUsoException extends RegraDeNegocioVioladaException {

    private static final long serialVersionUID = 1L;

    public CidadeEmUsoException(String mensagem) {
        super(mensagem);
    }

    public CidadeEmUsoException(Long id) {
        this(String.format("Cidade, com ID %d, não pode ser apagada por estar em uso.", id));
    }
}

