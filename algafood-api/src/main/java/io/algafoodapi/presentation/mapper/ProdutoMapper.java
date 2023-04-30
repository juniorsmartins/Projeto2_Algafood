package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Produto;
import io.algafoodapi.presentation.dto.request.ProdutoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.ProdutoDtoRequest;
import io.algafoodapi.presentation.dto.request.ProdutoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.ProdutoDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProdutoMapper implements PoliticaMapper<ProdutoDtoRequest, ProdutoDtoResponse,
    ProdutoPesquisarDtoRequest, ProdutoAtualizarDtoRequest, Produto, Long> {

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Produto converterDtoRequestParaEntidade(ProdutoDtoRequest dtoRequest) {
    return this.modelMapper.map(dtoRequest, Produto.class);
  }

  @Override
  public ProdutoDtoResponse converterEntidadeParaDtoResponse(Produto entidade) {
    return this.modelMapper.map(entidade, ProdutoDtoResponse.class);
  }

  @Override
  public Produto converterPesquisarDtoRequestParaEntidade(ProdutoPesquisarDtoRequest pesquisarDtoRequest) {
    return this.modelMapper.map(pesquisarDtoRequest, Produto.class);
  }

  @Override
  public Produto converterAtualizarDtoRequestParaEntidade(ProdutoAtualizarDtoRequest atualizarDtoRequest) {
    return this.modelMapper.map(atualizarDtoRequest, Produto.class);
  }

  @Override
  public Page<ProdutoDtoResponse> converterPaginaDeEntidadesParaPaginaDeDtoResponse(Page<Produto> entidades) {
    return entidades.map(this::converterEntidadeParaDtoResponse);
  }

  @Override
  public List<ProdutoDtoResponse> converterListaDeEntidadesParaListaDeDtoResponse(List<Produto> entidades) {
    return entidades.stream()
        .map(this::converterEntidadeParaDtoResponse)
        .toList();
  }
}

