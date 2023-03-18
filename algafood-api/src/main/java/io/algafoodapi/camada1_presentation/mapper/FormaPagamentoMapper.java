package io.algafoodapi.camada1_presentation.mapper;

import io.algafoodapi.camada1_presentation.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.camada1_presentation.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.camada2_business.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class FormaPagamentoMapper {

    private final ModelMapper modelMapper;

    public FormaPagamentoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamento converterDtoRequestParaEntidade(final FormaPagamentoDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, FormaPagamento.class);
    }

    public FormaPagamentoDtoResponse converterEntidadeParaDtoResponse(final FormaPagamento formaPagamento) {
        return this.modelMapper.map(formaPagamento, FormaPagamentoDtoResponse.class);
    }
}

