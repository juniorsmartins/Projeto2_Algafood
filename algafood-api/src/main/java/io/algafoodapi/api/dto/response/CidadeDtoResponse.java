package io.algafoodapi.api.dto.response;

import lombok.Builder;

@Builder
public record CidadeDtoResponse
    (
        Long id,
        String nome,
        EstadoDtoResponse estado
    )
{ }

