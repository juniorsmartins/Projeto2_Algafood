package io.algafoodapi.presentation.dto.response;

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
public final class GrupoResumoDtoResponse implements PoliticaResumoDtoResponse, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;
}

