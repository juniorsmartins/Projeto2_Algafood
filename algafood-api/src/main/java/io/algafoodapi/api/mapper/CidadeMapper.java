package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.CidadeDtoRequest;
import io.algafoodapi.api.dto.response.CidadeDtoResponse;
import io.algafoodapi.api.dto.response.EstadoDtoResponse;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class CidadeMapper {

    private final ModelMapper modelMapper;

    public CidadeMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cidade converterDtoRequestParaEntidade(final CidadeDtoRequest dto) {
        return Cidade.builder()
                .nome(dto.nome())
                .estado(Estado.builder()
                        .id(dto.estado().id())
                        .build())
                .build();
    }

    public CidadeDtoResponse converterEntidadeParaDtoResponse(final Cidade cidade) {
        return CidadeDtoResponse.builder()
                .id(cidade.getId())
                .nome(cidade.getNome())
                .estado(EstadoDtoResponse.builder()
                        .id(cidade.getEstado().getId())
                        .nome(cidade.getEstado().getNome())
                        .build())
                .build();
    }
}

