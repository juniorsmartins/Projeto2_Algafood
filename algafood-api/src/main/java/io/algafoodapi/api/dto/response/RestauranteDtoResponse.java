package io.algafoodapi.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.algafoodapi.domain.model.Endereco;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestauranteDtoResponse
    (
            Long id,
            String nome,
            BigDecimal taxaFrete,
            CozinhaDtoResponse cozinha,
            Endereco endereco,
            OffsetDateTime dataHoraUTCCadastro,
            OffsetDateTime dataHoraUTCAtualizacao
    )
{ }

