package io.algafoodapi.domain.exception.http409;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class CidadeEmUsoException extends EntidadeEmUsoException {

    private static final long serialVersionUID = 1L;

    public CidadeEmUsoException(String mensagem) {
        super(mensagem);
    }

    public CidadeEmUsoException(Long id) {
        this(String.format("Proibido apagar cidade, com ID %d, em uso.", id));
    }
}

