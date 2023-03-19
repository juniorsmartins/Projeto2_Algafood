package io.algafoodapi.business.exception.http409;

public final class RestauranteEmUsoException extends RegraDeNegocioVioladaException {

    private static final long serialVersionUID = 1L;

    public RestauranteEmUsoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteEmUsoException(Long id) {
        this(String.format("Restaurante, com ID %d, não pode ser apagado por estar em uso.", id));
    }
}

