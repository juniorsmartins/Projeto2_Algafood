package io.algafoodapi.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ProdutoPesquisarDtoRequest implements PoliticaPesquisarDtoRequest<Long>, Serializable {

  private static final Long serialVersionUID = 1L;

  private Long id;

  private String nome;

  private String descricao;

  private BigDecimal preco;

  private Boolean ativo;

  private RestauranteIdDtoRequest restaurante;
}

