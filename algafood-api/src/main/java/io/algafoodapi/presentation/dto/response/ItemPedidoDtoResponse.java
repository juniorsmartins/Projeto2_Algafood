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
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ItemPedidoDtoResponse implements PoliticaDtoResponse<Long>, Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long produtoId;

  private String produtoNome;

  private Integer quantidade;

  private BigDecimal precoUnitario;

  private BigDecimal precoTotal;

  private String observacao;
}

