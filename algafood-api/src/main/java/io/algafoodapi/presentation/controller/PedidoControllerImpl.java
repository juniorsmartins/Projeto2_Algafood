package io.algafoodapi.presentation.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.algafoodapi.business.service.PoliticaPedidoService;
import io.algafoodapi.presentation.dto.request.PedidoDtoRequest;
import io.algafoodapi.presentation.dto.response.PedidoDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/pedidos")
public final class PedidoControllerImpl implements PoliticaPedidoController<PedidoDtoRequest, PedidoDtoResponse, Long> {

  @Autowired
  private PoliticaPedidoService service;

  @Override
  public MappingJacksonValue listar(@RequestParam(required = false) final String campos) {

    var pedidosDtoResponse = this.service.listar();
    MappingJacksonValue pedidosEnvelopados = new MappingJacksonValue(pedidosDtoResponse);

    SimpleFilterProvider provedorDeFiltro = new SimpleFilterProvider();
    provedorDeFiltro.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

    if (campos != null && !campos.isBlank()) {
      provedorDeFiltro.addFilter("pedidoFilter", SimpleBeanPropertyFilter
          .filterOutAllExcept(campos.split(",")));
    }

    pedidosEnvelopados.setFilters(provedorDeFiltro);

    return pedidosEnvelopados;
  }
}

