package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.service.VendaQueryService;
import io.algafoodapi.presentation.dto.response.VendaDiaria;
import io.algafoodapi.presentation.filtros.VendaDiariaFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = {"/estatisticas"})
public class EstatisticasController {

  @Autowired
  private VendaQueryService vendaQueryService;

  @GetMapping(path = "/vendas-diarias")
  public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFiltro filtro) {
    return vendaQueryService.consultarVendasDiarias(filtro);
  }
}

