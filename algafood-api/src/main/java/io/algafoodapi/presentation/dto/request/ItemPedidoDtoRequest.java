package io.algafoodapi.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "")
public class ItemPedidoDtoRequest {

  @NotNull
  private Long produtoId;

  @NotBlank
  private String produtoNome;

  @NotNull
  @PositiveOrZero
  private Integer quantidade;

  @NotNull
  @Positive
  private BigDecimal precoUnitario;

  @NotNull
  @Positive
  private BigDecimal precoTotal;

  @Length(max = 250)
  private String observacao;
}

