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
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException naoEncontradaException, WebRequest request) {

        var httpStatus = HttpStatus.NOT_FOUND;
        var tipoDeErroEnum = TipoDeErroEnum.ENTIDADE_NAO_ENCONTRADA;
        var detail = naoEncontradaException.getMessage();

        var mensagemDeErro = this.criarMensagemErrorBuilder(httpStatus, tipoDeErroEnum, detail).build();

        return this.handleExceptionInternal(naoEncontradaException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException emUsoException, WebRequest request) {

        return this.handleExceptionInternal(emUsoException, emUsoException.getMessage(), new HttpHeaders(),
                HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(RequisicaoMalFormuladaException.class)
    public ResponseEntity<?> tratarRequisicaoMalFormuladaException(RequisicaoMalFormuladaException malFormuladaException, WebRequest request) {

        return this.handleExceptionInternal(malFormuladaException, malFormuladaException.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = MensagemDeErro.builder()
                    .status(status.value())
//                    .type()
                    .title(status.getReasonPhrase()) // Devolve uma descrição sobre o status retornado na resposta
//                    .detail()
                    .dataHora(LocalDateTime.now())
                    .build();

        } else if (body instanceof String) {
            body = MensagemDeErro.builder()
                    .status(status.value())
//                    .type()
                    .title(body.toString())
//                    .detail()
                    .dataHora(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private MensagemDeErro.MensagemDeErroBuilder criarMensagemErrorBuilder(HttpStatus httpStatus,
                                                                           TipoDeErroEnum tipoDeErroEnum,
                                                                           String details) {
        return MensagemDeErro.builder()
                .status(httpStatus.value())
                .type(tipoDeErroEnum.getCaminho())
                .title(tipoDeErroEnum.getTitulo())
                .detail(details)
                .dataHora(LocalDateTime.now());
    }
}

