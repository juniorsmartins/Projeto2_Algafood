package io.algafoodapi.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "subtotal")
public final class PedidoDtoRequest implements PoliticaDtoRequest, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Valid
    private RestauranteIdDtoRequest restaurante;

    @NotNull
    @Valid
    private UsuarioIdDtoRequest usuario;

    @NotNull
    @Valid
    private FormaPagamentoIdDtoRequest formaPagamento;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<ItemPedidoDtoRequest> itensPedido;

    @NotNull
    @Valid
    private EnderecoDtoRequest enderecoEntrega;
}

