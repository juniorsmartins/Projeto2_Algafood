package io.algafoodapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioAtualizarDtoRequest implements PoliticaAtualizarDtoRequest<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private Long id;

    private String nome;

    private String email;
}

