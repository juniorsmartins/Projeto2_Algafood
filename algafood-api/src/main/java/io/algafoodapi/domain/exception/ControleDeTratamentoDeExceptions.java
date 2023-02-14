package io.algafoodapi.domain.exception;

import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.EntidadeEmUsoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public final class ControleDeTratamentoDeExceptions extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource mensagemInternacionalizada;

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> tratarEntidadeNaoEncontrada(EntidadeNaoEncontradaException naoEncontradaException,
                                                         WebRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var tipoDeErroEnum = TipoDeErroEnum.ENTIDADE_NAO_ENCONTRADA;
        var detalhe = naoEncontradaException.getMessage();

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(naoEncontradaException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> tratarEntidadeEmUso(EntidadeEmUsoException emUsoException, WebRequest request) {

        var httpStatus = HttpStatus.CONFLICT;
        var tipoDeErroEnum = TipoDeErroEnum.ENTIDADE_EM_USO;
        var detalhe = emUsoException.getMessage();

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(emUsoException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(RequisicaoMalFormuladaException.class)
    public ResponseEntity<Object> tratarRequisicaoMalFormulada(RequisicaoMalFormuladaException malFormuladaException,
                                                          WebRequest request) {

        var httpStatus = HttpStatus.BAD_REQUEST;
        var tipoDeErroEnum = TipoDeErroEnum.REQUISICAO_MAL_FORMULADA;
        var detalhe = malFormuladaException.getMessage();

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(malFormuladaException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var tipoDeErroEnum = TipoDeErroEnum.ERRO_DE_SISTEMA;
        var detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";

        // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
        // fazendo logging) para mostrar a stacktrace no console
        // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
        // para você durante, especialmente na fase de desenvolvimento
        ex.printStackTrace();

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(status, tipoDeErroEnum, detail).build();

        return handleExceptionInternal(ex, mensagemDeErro, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException notReadableException,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        var tipoDeErroEnum = TipoDeErroEnum.REQUISICAO_MAL_FORMULADA;
        var detalhe = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(status, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(notReadableException, mensagemDeErro, new HttpHeaders(),
                status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException argumentNotValid,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        var tipoDeErroEnum = TipoDeErroEnum.DADOS_INVALIDOS;
        List<MensagemDeErro> errosDeValidacao = new ArrayList<>();
        List<FieldError> fieldErrors = argumentNotValid.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erro -> {
            var mensagem = mensagemInternacionalizada.getMessage(erro, LocaleContextHolder.getLocale());
            var erroPersonalizado = this.criarMensagemDeErrorBuilder(status, tipoDeErroEnum, mensagem)
                    .statusHttp(status.name())
                    .anotacaoViolada(erro.getCode())
                    .campoDeErro(erro.getField())
                    .build();
            errosDeValidacao.add(erroPersonalizado);
        });

        return this.handleExceptionInternal(argumentNotValid, errosDeValidacao, new HttpHeaders(),
                status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = MensagemDeErro.builder()
                    .codigoHttp(status.value())
                    .titulo(status.getReasonPhrase()) // Devolve uma descrição sobre o status retornado na resposta
                    .dataHora(LocalDateTime.now())
                    .build();

        } else if (body instanceof String) {
            body = MensagemDeErro.builder()
                    .codigoHttp(status.value())
                    .titulo(body.toString())
                    .dataHora(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private MensagemDeErro.MensagemDeErroBuilder criarMensagemDeErrorBuilder(HttpStatus httpStatus,
                                                                             TipoDeErroEnum tipoDeErroEnum,
                                                                             String details) {
        return MensagemDeErro.builder()
                .codigoHttp(httpStatus.value())
                .esclarecimento(tipoDeErroEnum.getCaminho())
                .titulo(tipoDeErroEnum.getTitulo())
                .detalhe(details)
                .dataHora(LocalDateTime.now());
    }
}

