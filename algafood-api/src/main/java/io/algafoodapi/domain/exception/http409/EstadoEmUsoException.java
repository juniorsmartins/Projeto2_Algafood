package io.algafoodapi.domain.exception.http409;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class EstadoEmUsoException extends EntidadeEmUsoException {
    private static final long serialVersionUID = 1L;

    public EstadoEmUsoException(String mensagem) {
        super(mensagem);
    }

    public EstadoEmUsoException(Long id) {
        this(String.format("Proibido apagar estado, com ID %d, em uso.", id));
    }
}
