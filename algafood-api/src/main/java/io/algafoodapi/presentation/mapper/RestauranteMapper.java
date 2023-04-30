package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Restaurante;
import io.algafoodapi.presentation.dto.request.RestauranteAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.RestauranteDtoRequest;
import io.algafoodapi.presentation.dto.request.RestaurantePesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.RestauranteDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class RestauranteMapper implements PoliticaMapper<RestauranteDtoRequest, RestauranteDtoResponse,
    RestaurantePesquisarDtoRequest, RestauranteAtualizarDtoRequest, Restaurante, Long> {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante converterDtoRequestParaEntidade(final RestauranteDtoRequest dto) {
        return this.modelMapper.map(dto, Restaurante.class);
    }

    public RestauranteDtoResponse converterEntidadeParaDtoResponse(final Restaurante restaurante) {
        return this.modelMapper.map(restaurante, RestauranteDtoResponse.class);
    }

    public RestauranteDtoRequest converterEntidadeParaDtoRequest(final Restaurante restaurante) {
        return this.modelMapper.map(restaurante, RestauranteDtoRequest.class);
    }

    public void copiarValoresDaOrigemParaDestino(Restaurante restauranteUpdate, Restaurante restaurante) {
        this.modelMapper.map(restauranteUpdate, restaurante);
    }

    public Restaurante converterAtualizarDtoRequestParaEntidade(RestauranteAtualizarDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, Restaurante.class);
    }

    @Override
    public Page<RestauranteDtoResponse> converterPaginaDeEntidadesParaPaginaDeDtoResponse(Page<Restaurante> entidades) {
        return entidades.map(this::converterEntidadeParaDtoResponse);
    }

    @Override
    public List<RestauranteDtoResponse> converterListaDeEntidadesParaListaDeDtoResponse(List<Restaurante> entidades) {
        return entidades.stream().map(this::converterEntidadeParaDtoResponse).toList();
    }

    public Restaurante converterPesquisarDtoRequestParaEntidade(RestaurantePesquisarDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, Restaurante.class);
    }
}

