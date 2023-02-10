package io.algafoodapi.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensagemDeErro {

    private String mensagem;
    private HttpStatus status;
    private LocalDateTime dataHora;
}

