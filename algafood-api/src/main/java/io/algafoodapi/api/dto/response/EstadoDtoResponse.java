package io.algafoodapi.api.dto.response;

import lombok.Builder;

@Builder
public record EstadoDtoResponse
    (
        Long id,
        String nome
    )
{ }

