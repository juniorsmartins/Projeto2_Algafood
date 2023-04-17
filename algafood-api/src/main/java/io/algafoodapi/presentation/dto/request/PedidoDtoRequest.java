package io.algafoodapi.presentation.dto.request;

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
@EqualsAndHashCode(of = "subtotal")
public final class PedidoDtoRequest implements PoliticaDtoRequest, Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete = BigDecimal.ZERO;

    private BigDecimal valorTotal;
}

