package io.algafoodapi.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UsuarioAtualizarDtoRequest implements PoliticaAtualizarDtoRequest<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Length(max = 100)
    private String nome;

    @NotBlank
    @Email
    @Length(max = 150)
    private String email;

    @Valid
    private List<GrupoIdDtoRequest> grupos;

    @Valid
    private List<PedidoIdDtoRequest> pedidos;
}

