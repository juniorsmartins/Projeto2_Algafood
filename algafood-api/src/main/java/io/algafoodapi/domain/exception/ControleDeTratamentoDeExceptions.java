package io.algafoodapi.domain.exception;

import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.EntidadeEmUsoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public final class ControleDeTratamentoDeExceptions {

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

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<MensagemDeErro> tratarHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException mediaTypeNotSupportedException) {

        var httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        var mensagem = MensagemDeErro.builder()
                .mensagem("Tipo de mídia não aceito!")
                .status(httpStatus)
                .dataHora(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(mensagem);
    }
}

