package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Permissao;
import io.algafoodapi.presentation.dto.request.PermissaoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.PermissaoDtoRequest;
import io.algafoodapi.presentation.dto.request.PermissaoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.PermissaoDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PermissaoMapper implements PoliticaMapper<PermissaoDtoRequest, PermissaoDtoResponse,
    PermissaoPesquisarDtoRequest, PermissaoAtualizarDtoRequest, Permissao, Long> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Permissao converterDtoRequestParaEntidade(PermissaoDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, Permissao.class);
    }

    @Override
    public PermissaoDtoResponse converterEntidadeParaDtoResponse(Permissao entidade) {
        return this.modelMapper.map(entidade, PermissaoDtoResponse.class);
    }

    @Override
    public Permissao converterPesquisarDtoRequestParaEntidade(PermissaoPesquisarDtoRequest pesquisarDtoRequest) {
        return this.modelMapper.map(pesquisarDtoRequest, Permissao.class);
    }

    @Override
    public Permissao converterAtualizarDtoRequestParaEntidade(PermissaoAtualizarDtoRequest atualizarDtoRequest) {
        return this.modelMapper.map(atualizarDtoRequest, Permissao.class);
    }

    @Override
    public Page<PermissaoDtoResponse> converterPaginaDeEntidadesParaPaginaDeDtoResponse(Page<Permissao> entidades) {
        return entidades.map(this::converterEntidadeParaDtoResponse);
    }

    @Override
    public List<PermissaoDtoResponse> converterListaDeEntidadesParaListaDeDtoResponse(List<Permissao> entidades) {
        return entidades.stream()
            .map(this::converterEntidadeParaDtoResponse)
            .toList();
    }
}

