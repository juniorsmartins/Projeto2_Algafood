package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Restaurante;
import io.algafoodapi.presentation.dto.request.RestauranteAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.RestauranteDtoRequest;
import io.algafoodapi.presentation.dto.request.RestaurantePesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.RestauranteDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class RestauranteMapper {

    private final ModelMapper modelMapper;

    public RestauranteMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

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

    public Restaurante converterPesquisarDtoRequestParaEntidade(RestaurantePesquisarDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, Restaurante.class);
    }

    public Page<RestauranteDtoResponse> converterPaginaDeRestaurantesParaPaginaDeDtoResponse(Page<Restaurante> entidades) {
        return entidades.map(this::converterEntidadeParaDtoResponse);
    }

    public List<RestauranteDtoResponse> converterListaDeRestaurantesParaListaDeDtoResponse(List<Restaurante> entidades) {
        return entidades.stream().map(this::converterEntidadeParaDtoResponse).toList();
    }
}

