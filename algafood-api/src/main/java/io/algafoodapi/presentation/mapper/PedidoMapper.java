package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.presentation.dto.request.PedidoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.PedidoDtoRequest;
import io.algafoodapi.presentation.dto.request.PedidoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.PedidoDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class PedidoMapper implements PoliticaMapper<PedidoDtoRequest, PedidoDtoResponse,
    PedidoPesquisarDtoRequest, PedidoAtualizarDtoRequest, Pedido, Long> {

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Pedido converterDtoRequestParaEntidade(PedidoDtoRequest dtoRequest) {
    return this.modelMapper.map(dtoRequest, Pedido.class);
  }

  @Override
  public PedidoDtoResponse converterEntidadeParaDtoResponse(Pedido entidade) {
    return this.modelMapper.map(entidade, PedidoDtoResponse.class);
  }

  @Override
  public Pedido converterPesquisarDtoRequestParaEntidade(PedidoPesquisarDtoRequest pesquisarDtoRequest) {
    return this.modelMapper.map(pesquisarDtoRequest, Pedido.class);
  }

  @Override
  public Pedido converterAtualizarDtoRequestParaEntidade(PedidoAtualizarDtoRequest atualizarDtoRequest) {
    return this.modelMapper.map(atualizarDtoRequest, Pedido.class);
  }

  @Override
  public Page<PedidoDtoResponse> converterPaginaDeEntidadesParaPaginaDeDtoResponse(Page<Pedido> entidades) {
    return entidades.map(this::converterEntidadeParaDtoResponse);
  }

  @Override
  public List<PedidoDtoResponse> converterListaDeEntidadesParaListaDeDtoResponse(List<Pedido> entidades) {
    return entidades.stream()
        .map(this::converterEntidadeParaDtoResponse)
        .toList();
  }
}
