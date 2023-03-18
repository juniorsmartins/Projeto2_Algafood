package io.algafoodapi.camada1_presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"cep", "bairro", "logradouro", "numero"})
public final class EnderecoDtoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Length(max = 15)
    private String cep;

    @NotBlank
    @Length(max = 100)
    private String bairro;

    @NotBlank
    @Length(max = 100)
    private String logradouro;

    @NotBlank
    @Length(max = 10)
    private String numero;

    @Length(max = 250)
    private String complemento;

    @NotNull
    @Valid
    private CidadeIdRequest cidade;
}

