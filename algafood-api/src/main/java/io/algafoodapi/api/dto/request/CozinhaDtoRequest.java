package io.algafoodapi.api.dto.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record CozinhaDtoRequest
    (
        @NotBlank
        String nome
    )
{ }

