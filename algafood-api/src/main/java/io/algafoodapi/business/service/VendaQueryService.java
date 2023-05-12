package io.algafoodapi.business.service;

import io.algafoodapi.presentation.dto.response.VendaDiaria;
import io.algafoodapi.presentation.filtros.VendaDiariaFiltro;

import java.util.List;

public interface VendaQueryService {

  List<VendaDiaria> consultarVendasDiarias(VendaDiariaFiltro filtro);
}

