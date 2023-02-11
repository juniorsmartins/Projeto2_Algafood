package io.algafoodapi.domain.exception;

import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.EntidadeEmUsoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@RestControllerAdvice
public final class ControleDeTratamentoDeExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException naoEncontradaException, WebRequest request) {

        return this.handleExceptionInternal(naoEncontradaException, naoEncontradaException.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> tratarEntidadeEmUsoException(EntidadeEmUsoException emUsoException, WebRequest request) {

        return this.handleExceptionInternal(emUsoException, emUsoException.getMessage(), new HttpHeaders(),
                HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(RequisicaoMalFormuladaException.class)
    public ResponseEntity<Object> tratarRequisicaoMalFormuladaException(RequisicaoMalFormuladaException malFormuladaException, WebRequest request) {

        return this.handleExceptionInternal(malFormuladaException, malFormuladaException.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = MensagemDeErro.builder()
                    .mensagem(status.getReasonPhrase()) // Devolve uma descrição sobre o status retornado na resposta
                    .status(status)
                    .dataHora(LocalDateTime.now())
                    .build();

        } else if (body instanceof String) {
            body = MensagemDeErro.builder()
                    .mensagem(body.toString())
                    .status(status)
                    .dataHora(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}

