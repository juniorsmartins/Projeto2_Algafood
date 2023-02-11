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

    private Integer status; // Código Http
    private String titulo; // Descrição do problema para humanos
    private String detalhe; // Descrição detalhada e específica sobre a ocorrência do erro
    private String esclarecimento; // URI para especificar o tipo do problema
    private LocalDateTime dataHora;
}

