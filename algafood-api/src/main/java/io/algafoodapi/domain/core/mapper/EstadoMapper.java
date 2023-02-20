package io.algafoodapi.domain.core.mapper;

import io.algafoodapi.api.dto.request.EstadoDtoRequest;
import io.algafoodapi.api.dto.response.EstadoDtoResponse;
import io.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Estado converterDTORequestParaEntidade( EstadoDtoRequest dto) {
        return Estado.builder()
                .nome(dto.nome())
                .build();
    }

    public EstadoDtoResponse converterEntidadeParaDTOResponse(Estado estado) {
        return EstadoDtoResponse.builder()
                .id(estado.getId())
                .nome(estado.getNome())
                .build();
    }

}
