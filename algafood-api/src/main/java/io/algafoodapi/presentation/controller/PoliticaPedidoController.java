package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.PoliticaDtoRequest;
import io.algafoodapi.presentation.dto.response.PoliticaDtoResponse;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;

public interface PoliticaPedidoController<R extends PoliticaDtoRequest, S extends PoliticaDtoResponse<I>, I> {

  @GetMapping(path = {"/listar-campos-escolhidos"})
  MappingJacksonValue listarCamposEscolhidos(String campos);
}

