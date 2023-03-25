package io.algafoodapi.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RestauranteDtoResponse implements PoliticaDtoResponse<Long> {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaDtoResponse cozinha;

    private EnderecoDtoResponse endereco;

    private OffsetDateTime dataHoraUTCCadastro;

    private OffsetDateTime dataHoraUTCAtualizacao;

    private Boolean ativo;

    private List<FormaPagamentoDtoResponse> formasPagamento;
}

