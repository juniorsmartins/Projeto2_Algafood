package io.algafoodapi.domain.exception;

import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.EntidadeEmUsoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public final class ControleDeTratamentoDeExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<MensagemDeErro> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException naoEncontradaException) {

        var httpStatus = HttpStatus.NOT_FOUND;

        var mensagem = MensagemDeErro.builder()
                .mensagem(naoEncontradaException.getMessage())
                .status(httpStatus)
                .dataHora(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(mensagem);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<MensagemDeErro> tratarEntidadeEmUsoException(EntidadeEmUsoException emUsoException) {

        var httpStatus = HttpStatus.CONFLICT;

        var mensagem = MensagemDeErro.builder()
                .mensagem(emUsoException.getMessage())
                .status(httpStatus)
                .dataHora(LocalDateTime.now())
                .build();


        return ResponseEntity
                .status(httpStatus)
                .body(mensagem);
    }

    @ExceptionHandler(RequisicaoMalFormuladaException.class)
    public ResponseEntity<MensagemDeErro> tratarRequisicaoMalFormuladaException(RequisicaoMalFormuladaException malFormuladaException) {

        var httpStatus = HttpStatus.BAD_REQUEST;

        var mensagem = MensagemDeErro.builder()
                .mensagem(malFormuladaException.getMessage())
                .status(httpStatus)
                .dataHora(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(mensagem);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        body = MensagemDeErro.builder()
                .mensagem(status.getReasonPhrase())
                .dataHora(LocalDateTime.now())
                .build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}

