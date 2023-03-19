package io.algafoodapi.presentation.mapper;

import io.algafoodapi.presentation.dto.request.CozinhaDtoRequest;
import io.algafoodapi.presentation.dto.response.CozinhaDtoResponse;
import io.algafoodapi.business.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class CozinhaMapper {

    private final ModelMapper modelMapper;

    public CozinhaMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cozinha converterDtoRequestParaEntidade(final CozinhaDtoRequest dto) {
        return this.modelMapper.map(dto, Cozinha.class);
    }

    public CozinhaDtoResponse converterEntidadeParaDtoResponse(final Cozinha cozinha) {
        return this.modelMapper.map(cozinha, CozinhaDtoResponse.class);
    }
}

