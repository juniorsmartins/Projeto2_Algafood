package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.GrupoDtoRequest;
import io.algafoodapi.api.dto.request.GrupoPesquisarDtoRequest;
import io.algafoodapi.api.dto.response.GrupoDtoResponse;
import io.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public final class GrupoMapper {

    private final ModelMapper modelMapper;

    public GrupoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Grupo converterDtoRequestParaEntidade(GrupoDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, Grupo.class);
    }

    public Grupo converterPesquisarDtoRequestParaEntidade(GrupoPesquisarDtoRequest pesquisarDtoRequest) {
        return this.modelMapper.map(pesquisarDtoRequest, Grupo.class);
    }

    public GrupoDtoResponse converterEntidadeParaDtoResponse(Grupo grupo) {
        return this.modelMapper.map(grupo, GrupoDtoResponse.class);
    }

    public Page<GrupoDtoResponse> converterPaginaDeEntidadeParaPaginaDeDtoResponse(Page<Grupo> grupos) {
        return grupos.map(this::converterEntidadeParaDtoResponse);
    }
}

