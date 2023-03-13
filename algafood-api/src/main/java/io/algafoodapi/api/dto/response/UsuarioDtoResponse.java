package io.algafoodapi.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.algafoodapi.api.dto.request.PoliticaDtoResponse;
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
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UsuarioDtoResponse implements PoliticaDtoResponse<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String email;
}

