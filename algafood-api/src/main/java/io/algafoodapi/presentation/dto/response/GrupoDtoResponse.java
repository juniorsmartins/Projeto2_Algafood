package io.algafoodapi.presentation.dto.response;

import io.algafoodapi.business.model.Permissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class GrupoDtoResponse implements PoliticaDtoResponse<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private Set<Permissao> permissoes;
}

