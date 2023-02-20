package io.algafoodapi.api.dto.request;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
public record CozinhaIdRequest
    (
        @NotNull
        @Positive
        Long id
    )
{ }

