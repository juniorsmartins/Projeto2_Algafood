package io.algafoodapi.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MensagemDeErro {

    // Padrão RFC 7807

    private Integer codigoHttp; // Código Http (Integer)
    private String statusHttp; // Nome Http (String)
    private String anotacaoViolada; // Nome da anotação violada do Bean Validation
    private String campoDeErro; // Nome do campo em que a anotação foi violada
    private String titulo; // Descrição do problema para humanos
    private String detalhe; // Descrição detalhada e específica sobre a ocorrência do erro
    private String esclarecimento; // URI para especificar o tipo do problema
    private LocalDateTime dataHora;
}

