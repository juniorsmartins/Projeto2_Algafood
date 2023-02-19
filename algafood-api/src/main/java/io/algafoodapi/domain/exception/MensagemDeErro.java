package io.algafoodapi.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MensagemDeErro {

    // Padrão RFC 7807

    private Integer codigoHttp; // Código Http (Integer)
    private String statusHttp; // Nome Http (String)
    private String tipoDeErro; // Tipo do problema
    private String detalhe; // Descrição detalhada e específica sobre a ocorrência do erro
    private String linkParaEsclarecer; // URI para especificar o tipo do problema
    private OffsetDateTime dataHora;
    private List<Erro> erros;

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Erro {
        private String anotacaoViolada; // Nome da anotação violada do Bean Validation
        private String localDeErro; // Nome do campo em que a anotação foi violada
        private String explicacao;
    }
}

