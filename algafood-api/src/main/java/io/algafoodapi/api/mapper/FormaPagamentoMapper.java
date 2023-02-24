package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.api.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class FormaPagamentoMapper {

    private final ModelMapper modelMapper;

    public FormaPagamentoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamento converterDtoRequestParaEntidade(final FormaPagamentoDtoRequest dto) {
        return this.modelMapper.map(dto, FormaPagamento.class);
    }

    public FormaPagamentoDtoResponse converterEntidadeParaDtoResponse(final FormaPagamento formaPagamento) {
        return this.modelMapper.map(formaPagamento, FormaPagamentoDtoResponse.class);
    }
}

