package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.presentation.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.presentation.dto.response.FormaPagamentoDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<FormaPagamentoDtoResponse> converterListaDeFormasPagamentoParaListaDeDtoResponse(List<FormaPagamento> entidades) {
        return entidades.stream().map(this::converterEntidadeParaDtoResponse).toList();
    }
}

