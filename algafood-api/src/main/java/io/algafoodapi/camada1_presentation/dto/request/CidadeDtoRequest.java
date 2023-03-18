package io.algafoodapi.camada1_presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@EqualsAndHashCode(of = {"nome", "estado"})
public final class CidadeDtoRequest implements Serializable {

        private static final long serialVersionUID = 1L;

        @NotBlank
        private String nome;

        @NotNull
        @Valid
        private EstadoIdRequest estado;
}

