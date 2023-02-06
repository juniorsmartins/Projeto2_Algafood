package io.algafoodapi.domain.exception.http400;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class RequisicaoMalFormuladaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RequisicaoMalFormuladaException(String mensagem) {
        super(mensagem);
    }
}
