package io.algafoodapi.business.service;

import io.algafoodapi.infraestrutura.repository.PoliticaPedidoRepository;
import io.algafoodapi.presentation.dto.response.PedidoDtoResponse;
import io.algafoodapi.presentation.mapper.PedidoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PoliticaPedidoService {

  @Autowired
  private PoliticaPedidoRepository pedidoRepository;

  @Autowired
  private PedidoMapper mapper;

  @Override
  public List<PedidoDtoResponse> listar() {

    return this.pedidoRepository.buscarTodos()
      .stream()
      .map(this.mapper::converterEntidadeParaDtoResponse)
      .toList();
  }
}

