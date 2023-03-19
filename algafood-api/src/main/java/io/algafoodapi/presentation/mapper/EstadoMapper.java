package io.algafoodapi.presentation.mapper;

import io.algafoodapi.presentation.dto.request.EstadoDtoRequest;
import io.algafoodapi.presentation.dto.response.EstadoDtoResponse;
import io.algafoodapi.business.model.Estado;
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

