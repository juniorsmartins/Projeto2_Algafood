package io.algafoodapi.api.dto.request;

import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
public record CidadeDtoRequest
    (
        @NotBlank
        String nome,

        @NotNull
        @Valid
        EstadoIdRequest estado
    )
{ }

