package io.algafoodapi.presentation.dto.response;

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
import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class PedidoDtoResponse implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private OffsetDateTime dataHoraCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    private StatusPedidoEnum statusPedido;

//    private List<ItemPedido> itensPedido = new ArrayList<>();

    private FormaPagamentoDtoResponse formaPagamento;

    private RestauranteDtoResponse restaurante;

//    private Usuario usuario;

    private EnderecoDtoResponse enderecoEntrega;
}
