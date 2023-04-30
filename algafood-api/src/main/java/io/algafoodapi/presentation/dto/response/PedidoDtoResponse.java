package io.algafoodapi.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.algafoodapi.business.model.enuns.StatusPedidoEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@JsonFilter("pedidoFilter")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class PedidoDtoResponse implements PoliticaDtoResponse<Long>, Serializable {

    public static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private OffsetDateTime dataHoraCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum statusPedido;

//    private List<ItemPedido> itensPedido = new ArrayList<>();

    private FormaPagamentoDtoResponse formaPagamento;

    private RestauranteDtoResponse restaurante;

//    private Usuario usuario;

    private EnderecoDtoResponse enderecoEntrega;
}

