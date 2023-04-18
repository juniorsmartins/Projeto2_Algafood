package io.algafoodapi.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.algafoodapi.business.model.views.RestauranteView;
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
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RestauranteDtoResponse implements PoliticaDtoResponse<Long>, Serializable {

    public static final long serialVersionUID = 1L;

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    private OffsetDateTime dataHoraUTCCadastro;

    private OffsetDateTime dataHoraUTCAtualizacao;

    private Boolean ativo;

    private Boolean aberto;

    private List<FormaPagamentoDtoResponse> formasPagamento;

    private List<ProdutoDtoResponse> produtos;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDtoResponse cozinha;

    private EnderecoDtoResponse endereco;
}

