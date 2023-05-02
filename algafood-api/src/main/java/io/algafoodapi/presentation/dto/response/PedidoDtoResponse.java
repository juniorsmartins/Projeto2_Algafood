package io.algafoodapi.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonFilter("pedidoFilter")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PedidoDtoResponse implements PoliticaDtoResponse<Long>, Serializable {

    public static final long serialVersionUID = 1L;

    private Long id;

//    private BigDecimal subtotal;
//
//    private BigDecimal taxaFrete;
//
//    private BigDecimal valorTotal;

//    private OffsetDateTime dataHoraCriacao;
//
//    private OffsetDateTime dataConfirmacao;
//
//    private OffsetDateTime dataCancelamento;
//
//    private OffsetDateTime dataEntrega;

//    @Enumerated(EnumType.STRING)
//    private StatusPedidoEnum statusPedido;

    private List<ItemPedidoDtoResponse> itensPedido = new ArrayList<>();

    private FormaPagamentoDtoResponse formaPagamento;

    private RestauranteDtoResponse restaurante;

    private UsuarioDtoResponse usuario;

    private EnderecoDtoResponse enderecoEntrega;
}

