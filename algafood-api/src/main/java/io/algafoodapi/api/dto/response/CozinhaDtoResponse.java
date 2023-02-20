package io.algafoodapi.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CozinhaDtoResponse
    (
        Long id,

        @JsonProperty("gastronomia")
        String nome
    )
{ }

