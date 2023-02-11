package io.algafoodapi.domain.exception;

import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.EntidadeEmUsoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@RestControllerAdvice
public final class ControleDeTratamentoDeExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException naoEncontradaException, WebRequest request) {

        var httpStatus = HttpStatus.NOT_FOUND;
        var tipoDeErroEnum = TipoDeErroEnum.ENTIDADE_NAO_ENCONTRADA;
        var detalhe = naoEncontradaException.getMessage();

        var mensagemDeErro = this.criarMensagemErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(naoEncontradaException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException emUsoException, WebRequest request) {

        var httpStatus = HttpStatus.CONFLICT;
        var tipoDeErroEnum = TipoDeErroEnum.ENTIDADE_EM_USO;
        var detalhe = emUsoException.getMessage();

        var mensagemDeErro = this.criarMensagemErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(emUsoException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(RequisicaoMalFormuladaException.class)
    public ResponseEntity<?> tratarRequisicaoMalFormuladaException(RequisicaoMalFormuladaException malFormuladaException, WebRequest request) {

        var httpStatus = HttpStatus.BAD_REQUEST;
        var tipoDeErroEnum = TipoDeErroEnum.REQUISICAO_MAL_FORMULADA;
        var detalhe = malFormuladaException.getMessage();

        var mensagemDeErro = this.criarMensagemErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(malFormuladaException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = MensagemDeErro.builder()
                    .status(status.value())
                    .titulo(status.getReasonPhrase()) // Devolve uma descrição sobre o status retornado na resposta
                    .dataHora(LocalDateTime.now())
                    .build();

        } else if (body instanceof String) {
            body = MensagemDeErro.builder()
                    .status(status.value())
                    .titulo(body.toString())
                    .dataHora(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException notReadableException,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        var tipoDeErroEnum = TipoDeErroEnum.REQUISICAO_MAL_FORMULADA;
        var detalhe = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        var mensagemDeErro = this.criarMensagemErrorBuilder(status, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(notReadableException, mensagemDeErro, new HttpHeaders(),
                status, request);
    }

    private MensagemDeErro.MensagemDeErroBuilder criarMensagemErrorBuilder(HttpStatus httpStatus,
                                                                           TipoDeErroEnum tipoDeErroEnum,
                                                                           String details) {
        return MensagemDeErro.builder()
                .status(httpStatus.value())
                .esclarecimento(tipoDeErroEnum.getCaminho())
                .titulo(tipoDeErroEnum.getTitulo())
                .detalhe(details)
                .dataHora(LocalDateTime.now());
    }
}

