package io.algafoodapi.domain.exception;

import io.algafoodapi.domain.core.validation.ValidacaoException;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public final class ControleDeTratamentoDeExceptions extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource mensagemInternacionalizada;

    @ExceptionHandler(value = EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> tratarEntidadeNaoEncontrada(EntidadeNaoEncontradaException naoEncontradaException,
                                                         WebRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var tipoDeErroEnum = TipoDeErroEnum.ENTIDADE_NAO_ENCONTRADA;
        var detalhe = naoEncontradaException.getMessage();

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(naoEncontradaException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(value = EntidadeEmUsoException.class)
    public ResponseEntity<Object> tratarEntidadeEmUso(EntidadeEmUsoException emUsoException, WebRequest request) {

        var httpStatus = HttpStatus.CONFLICT;
        var tipoDeErroEnum = TipoDeErroEnum.ENTIDADE_EM_USO;
        var detalhe = emUsoException.getMessage();

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(emUsoException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(value = RequisicaoMalFormuladaException.class)
    public ResponseEntity<Object> tratarRequisicaoMalFormulada(RequisicaoMalFormuladaException malFormuladaException,
                                                          WebRequest request) {

        var httpStatus = HttpStatus.BAD_REQUEST;
        var tipoDeErroEnum = TipoDeErroEnum.REQUISICAO_MAL_FORMULADA;
        var detalhe = malFormuladaException.getMessage();

        var mensagemDeErro = this.criarMensagemDeErrorBuilder(httpStatus, tipoDeErroEnum, detalhe).build();

        return this.handleExceptionInternal(malFormuladaException, mensagemDeErro, new HttpHeaders(),
                httpStatus, request);
    }

    @ExceptionHandler(value = Exception.class)
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

    @ExceptionHandler(value = ValidacaoException.class)
    public ResponseEntity<Object> tratarValidacaoException(ValidacaoException validacaoException, WebRequest request) {

        return this.construirResponseComMensagemDeErros(validacaoException, validacaoException.getBindingResult(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException argumentNotValid,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        return this.construirResponseComMensagemDeErros(argumentNotValid, argumentNotValid.getBindingResult(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
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
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = MensagemDeErro.builder()
                    .codigoHttp(status.value())
                    .tipoDeErro(status.getReasonPhrase()) // Devolve uma descrição sobre o status retornado na resposta
                    .dataHora(OffsetDateTime.now())
                    .build();

        } else if (body instanceof String) {
            body = MensagemDeErro.builder()
                    .codigoHttp(status.value())
                    .tipoDeErro(body.toString())
                    .dataHora(OffsetDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> construirResponseComMensagemDeErros(Exception exception,
                                                                       BindingResult bindingResult,
                                                                       HttpHeaders headers,
                                                                       HttpStatus status,
                                                                       WebRequest request) {

        var tipoDeErroEnum = TipoDeErroEnum.DADOS_INVALIDOS;
        var detalhe = "A requisição contém um ou mais dados inválidos. Preencha corretamente e tente novamente.";

        List<MensagemDeErro.Erro> erros = bindingResult.getAllErrors().stream()
                .map(error -> {
                    var mensagem = mensagemInternacionalizada.getMessage(error, LocaleContextHolder.getLocale());

                    var name = error.getObjectName();

                    if (error instanceof FieldError) {
                        name = ((FieldError) error).getField();
                    }

                    return MensagemDeErro.Erro.builder()
                            .anotacaoViolada(error.getCode())
                            .localDeErro(name)
                            .explicacao(mensagem)
                            .build();
                })
                .toList();

        var problema = this.criarMensagemDeErrorBuilder(status, tipoDeErroEnum, detalhe)
                .erros(erros)
                .build();

        return handleExceptionInternal(exception, problema, headers, status, request);
    }

    private MensagemDeErro.MensagemDeErroBuilder criarMensagemDeErrorBuilder(HttpStatus httpStatus,
                                                                             TipoDeErroEnum tipoDeErroEnum,
                                                                             String details) {
        return MensagemDeErro.builder()
                .codigoHttp(httpStatus.value())
                .statusHttp(httpStatus.name())
                .linkParaEsclarecer(tipoDeErroEnum.getCaminho())
                .tipoDeErro(tipoDeErroEnum.getTitulo())
                .detalhe(details)
                .dataHora(OffsetDateTime.now());
    }
}

