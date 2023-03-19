package io.algafoodapi.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"cep", "bairro", "logradouro", "numero"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class EnderecoDtoResponse {

    private String cep;

    private String bairro;

    private String logradouro;

    private String numero;

    private String complemento;

    private CidadeDtoResponse cidade;
}

