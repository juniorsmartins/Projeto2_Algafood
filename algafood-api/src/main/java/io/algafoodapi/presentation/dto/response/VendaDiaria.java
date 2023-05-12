package io.algafoodapi.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
public final class VendaDiaria {

  private Date data;
  private Long totalVendas;
  private BigDecimal totalFaturado;
}

