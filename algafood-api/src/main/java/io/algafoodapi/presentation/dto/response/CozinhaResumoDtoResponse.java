package io.algafoodapi.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.algafoodapi.business.model.views.RestauranteView;
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
public final class CozinhaResumoDtoResponse implements PoliticaResumoDtoResponse<Long>, Serializable {

        private static final long serialVersionUID = 1L;
        
        private Long id;

        @JsonView(RestauranteView.Resumo.class)
        private String nome;
}

