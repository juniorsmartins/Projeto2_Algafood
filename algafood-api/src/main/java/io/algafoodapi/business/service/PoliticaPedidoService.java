package io.algafoodapi.business.service;

import io.algafoodapi.presentation.dto.response.PedidoDtoResponse;

import java.util.List;

public interface PoliticaPedidoService {

  List<PedidoDtoResponse> listar();
}

