package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Grupo;
import io.algafoodapi.presentation.dto.request.GrupoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.GrupoDtoRequest;
import io.algafoodapi.presentation.dto.request.GrupoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.GrupoDtoResponse;
import io.algafoodapi.presentation.dto.response.GrupoResumoDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class GrupoMapper implements PoliticaMapper<GrupoDtoRequest, GrupoDtoResponse,
    GrupoPesquisarDtoRequest, GrupoAtualizarDtoRequest, GrupoResumoDtoResponse, Grupo, Long> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Grupo converterDtoRequestParaEntidade(GrupoDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, Grupo.class);
    }

    @Override
    public Grupo converterPesquisarDtoRequestParaEntidade(GrupoPesquisarDtoRequest pesquisarDtoRequest) {
        return this.modelMapper.map(pesquisarDtoRequest, Grupo.class);
    }

    @Override
    public Grupo converterAtualizarDtoRequestParaEntidade(GrupoAtualizarDtoRequest atualizarDtoRequest) {
        return this.modelMapper.map(atualizarDtoRequest, Grupo.class);
    }

    @Override
    public Page<GrupoDtoResponse> converterPaginaDeEntidadesParaPaginaDeDtoResponse(Page<Grupo> entidades) {
        return entidades.map(this::converterEntidadeParaDtoResponse);
    }

    @Override
    public GrupoDtoResponse converterEntidadeParaDtoResponse(Grupo grupo) {
        return this.modelMapper.map(grupo, GrupoDtoResponse.class);
    }

    @Override
    public List<GrupoDtoResponse> converterListaDeEntidadesParaListaDeDtoResponse(List<Grupo> entidades) {
        return entidades.stream()
            .map(this::converterEntidadeParaDtoResponse)
            .toList();
    }

    @Override
    public GrupoResumoDtoResponse converterEntidadeParaResumoDtoResponse(Grupo entidade) {
        return this.modelMapper.map(entidade, GrupoResumoDtoResponse.class);
    }

    @Override
    public List<GrupoResumoDtoResponse> converterListaDeEntidadesParaListaDeResumoDtoResponse(List<Grupo> entidades) {
        return entidades.stream()
            .map(this::converterEntidadeParaResumoDtoResponse)
            .toList();
    }
}

