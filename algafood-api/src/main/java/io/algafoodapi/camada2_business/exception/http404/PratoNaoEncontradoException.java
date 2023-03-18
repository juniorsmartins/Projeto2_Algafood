package io.algafoodapi.camada2_business.exception.http404;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class PratoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public PratoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PratoNaoEncontradoException(Long id) {
        this(String.format("Não encontrado prato com código %d.", id));
    }
}

