package io.algafoodapi.domain.exception.http409;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class RestauranteEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = 1L;

    public RestauranteEmUsoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteEmUsoException(Long id) {
        this(String.format("Proibido apagar restaurante, com ID %d, em uso.", id));
    }
}

