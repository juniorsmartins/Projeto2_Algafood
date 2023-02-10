package io.algafoodapi.domain.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class MensagemDeErro {

    private String mensagem;
    private HttpStatus status;
    private LocalDateTime dataHora;
}

