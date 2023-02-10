package io.algafoodapi.domain.exception.http409;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class CozinhaEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = 1L;

    public CozinhaEmUsoException(String mensagem) {
        super(mensagem);
    }

    public CozinhaEmUsoException(Long id) {
        this(String.format("Cozinha, com ID %d, não pode ser apagada por estar em uso.", id));
    }
}

