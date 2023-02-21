package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.CidadeDtoRequest;
import io.algafoodapi.api.dto.response.CidadeDtoResponse;
import io.algafoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class CidadeMapper {

    private final ModelMapper modelMapper;

    public CidadeMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cidade converterDtoRequestParaEntidade(final CidadeDtoRequest dto) {
        return this.modelMapper.map(dto, Cidade.class);
    }

    public CidadeDtoResponse converterEntidadeParaDtoResponse(final Cidade cidade) {
        return this.modelMapper.map(cidade, CidadeDtoResponse.class);
    }
}

