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
    private String type; // URI para especificar o tipo do problema
    private String title; // Descrição do problema para humanos
    private String detail; // Descrição detalhada e específica sobre a ocorrência do erro
    private LocalDateTime dataHora;
}

