package io.algafoodapi.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class PermissaoAtualizarDtoRequest implements PoliticaAtualizarDtoRequest<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private Long id;

    @Length(max = 100)
    private String nome;

    @Length(max = 250)
    private String descricao;
}

