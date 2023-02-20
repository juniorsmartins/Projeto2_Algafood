package io.algafoodapi.domain.core.mapper;

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
        return Estado.builder()
                .nome(dto.nome())
                .build();
    }

    public EstadoDtoResponse converterEntidadeParaDtoResponse(final Estado estado) {
        return EstadoDtoResponse.builder()
                .id(estado.getId())
                .nome(estado.getNome())
                .build();
    }

}
