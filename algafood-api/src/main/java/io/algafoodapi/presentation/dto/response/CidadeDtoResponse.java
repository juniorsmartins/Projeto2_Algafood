package io.algafoodapi.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "codigo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CidadeDtoResponse implements Serializable {

        private static final long serialVersionUID = 1L;

        private String codigo;

        private String nome;

        private EstadoDtoResponse estado;
}

