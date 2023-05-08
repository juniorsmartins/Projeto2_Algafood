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
import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "subtotal")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PedidoResumoDtoResponse implements PoliticaResumoDtoResponse, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private String statusPedido;

    private OffsetDateTime dataHoraCriacao;

    private RestauranteResumoDtoResponse restaurante;

    private UsuarioResumoDtoResponse usuario;
}

