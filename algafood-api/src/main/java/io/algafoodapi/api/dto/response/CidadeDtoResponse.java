package io.algafoodapi.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CidadeDtoResponse
    (
        Long id,
        String nome,
        EstadoDtoResponse estado
    )
{ }

