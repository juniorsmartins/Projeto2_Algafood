package io.algafoodapi.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "descricao")
public final class FormaPagamentoAtualizarDtoRequest implements PoliticaAtualizarDtoRequest<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Length(max = 250)
    private String nome;
}

