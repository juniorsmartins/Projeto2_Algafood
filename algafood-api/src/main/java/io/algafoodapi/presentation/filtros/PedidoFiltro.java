package io.algafoodapi.presentation.filtros;

import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.business.model.Restaurante;
import io.algafoodapi.business.model.Usuario;
import io.algafoodapi.business.model.enuns.StatusPedidoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFiltro {

  private Long id;

  private StatusPedidoEnum statusPedido;

  private FormaPagamento formaPagamento;

  private Restaurante restaurante;

  private Usuario usuario;
}
