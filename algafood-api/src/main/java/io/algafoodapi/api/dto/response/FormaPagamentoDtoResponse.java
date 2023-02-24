package io.algafoodapi.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormaPagamentoDtoResponse {

    private Long id;
    private String descricao;
}

