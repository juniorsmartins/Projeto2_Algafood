package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.EstadoDtoRequest;
import io.algafoodapi.api.dto.response.EstadoDtoResponse;
import io.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper {

    private final ModelMapper modelMapper;

    public EstadoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Estado converterDtoRequestParaEntidade(final EstadoDtoRequest dto) {
        return this.modelMapper.map(dto, Estado.class);
    }

    public EstadoDtoResponse converterEntidadeParaDtoResponse(final Estado estado) {
        return this.modelMapper.map(estado, EstadoDtoResponse.class);
    }
}

