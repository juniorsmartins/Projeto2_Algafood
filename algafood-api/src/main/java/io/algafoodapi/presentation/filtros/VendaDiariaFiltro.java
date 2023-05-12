package io.algafoodapi.presentation.filtros;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
public class VendaDiariaFiltro {

  private Long restauranteId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime dataCriacaoInicio;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime dataCriacaoFim;
}

