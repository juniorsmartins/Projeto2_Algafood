package io.algafoodapi.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "")
public class ItemPedidoDtoRequest {

  private Long produtoId;

  private String produtoNome;

  private Integer quantidade;

  private BigDecimal precoUnitario;

  private BigDecimal precoTotal;

  private String observacao;
}

