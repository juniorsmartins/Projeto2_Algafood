package io.algafoodapi.api.dto.request;

import io.algafoodapi.domain.core.validation.GruposValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CozinhaDTOIn
    (
        @NotNull(groups = GruposValid.CozinhaId.class)
        Long id,

        @NotBlank
        String nome
    )
{ }

