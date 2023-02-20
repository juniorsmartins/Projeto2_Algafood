package io.algafoodapi.domain.core.mapper;

import io.algafoodapi.api.dto.request.EstadoDTORequest;
import io.algafoodapi.api.dto.response.EstadoDTOResponse;
import io.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Estado converterDTORequestParaEntidade( EstadoDTORequest dto) {
//        return this.modelMapper.map(dto, Estado.class);

        return Estado.builder()
                .nome(dto.nome())
                .build();
    }

    public EstadoDTOResponse converterEntidadeParaDTOResponse(Estado estado) {
        return EstadoDTOResponse.builder()
                .id(estado.getId())
                .nome(estado.getNome())
                .build();
    }

}
