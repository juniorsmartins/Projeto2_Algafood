package io.algafoodapi.api.dto.response;

import lombok.Builder;

@Builder
public record EstadoDTOResponse
    (
        Long id,
        String nome
    )
{ }

