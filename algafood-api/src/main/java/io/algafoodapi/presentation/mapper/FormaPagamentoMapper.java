package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.presentation.dto.request.FormaPagamentoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.presentation.dto.request.FormaPagamentoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.FormaPagamentoDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class FormaPagamentoMapper implements PoliticaMapper<FormaPagamentoDtoRequest, FormaPagamentoDtoResponse,
    FormaPagamentoPesquisarDtoRequest, FormaPagamentoAtualizarDtoRequest, FormaPagamento, Long> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FormaPagamento converterDtoRequestParaEntidade(FormaPagamentoDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, FormaPagamento.class);
    }

    @Override
    public FormaPagamentoDtoResponse converterEntidadeParaDtoResponse(FormaPagamento entidade) {
        return this.modelMapper.map(entidade, FormaPagamentoDtoResponse.class);
    }

    @Override
    public FormaPagamento converterPesquisarDtoRequestParaEntidade(FormaPagamentoPesquisarDtoRequest pesquisarDtoRequest) {
        return this.modelMapper.map(pesquisarDtoRequest, FormaPagamento.class);
    }

    @Override
    public FormaPagamento converterAtualizarDtoRequestParaEntidade(FormaPagamentoAtualizarDtoRequest atualizarDtoRequest) {
        return this.modelMapper.map(atualizarDtoRequest, FormaPagamento.class);
    }

    @Override
    public Page<FormaPagamentoDtoResponse> converterPaginaDeEntidadesParaPaginaDeDtoResponse(Page<FormaPagamento> entidades) {
        return entidades.map(this::converterEntidadeParaDtoResponse);
    }

    @Override
    public List<FormaPagamentoDtoResponse> converterListaDeEntidadesParaListaDeDtoResponse(List<FormaPagamento> entidades) {
        return entidades.stream()
            .map(this::converterEntidadeParaDtoResponse)
            .toList();
    }
}

