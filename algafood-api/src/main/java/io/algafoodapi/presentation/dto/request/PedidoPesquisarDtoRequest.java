package io.algafoodapi.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.business.model.Restaurante;
import io.algafoodapi.business.model.Usuario;
import io.algafoodapi.business.model.enuns.StatusPedidoEnum;
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
public final class PedidoPesquisarDtoRequest implements PoliticaPesquisarDtoRequest<Long>, Serializable {

  private static final Long serialVersionUID = 1L;

  private Long id;

  private BigDecimal subtotal;

  private BigDecimal taxaFrete;

  private BigDecimal valorTotal;

  private StatusPedidoEnum statusPedido;

  private FormaPagamento formaPagamento;

  private Restaurante restaurante;

  private Usuario usuario;
}

