package io.algafoodapi.api.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record EstadoDTORequest
    (
        @NotBlank
        @Length(max = 100)
        String nome
    )
{ }

