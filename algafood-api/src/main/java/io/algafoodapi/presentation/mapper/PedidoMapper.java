package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.presentation.dto.response.PedidoDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class PedidoMapper {

  @Autowired
  private ModelMapper modelMapper;

  public PedidoDtoResponse converterEntidadeParaDtoResponse(Pedido entidade) {
    return this.modelMapper.map(entidade, PedidoDtoResponse.class);
  }
}
