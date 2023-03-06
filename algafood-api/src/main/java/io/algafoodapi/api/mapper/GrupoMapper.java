package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.GrupoDtoRequest;
import io.algafoodapi.api.dto.response.GrupoDtoResponse;
import io.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
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

    public GrupoDtoResponse converterEntidadeParaDtoResponse(Grupo grupo) {
        return this.modelMapper.map(grupo, GrupoDtoResponse.class);
    }
}

