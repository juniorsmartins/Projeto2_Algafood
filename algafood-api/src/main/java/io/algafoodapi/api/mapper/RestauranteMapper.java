package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.RestauranteDtoRequest;
import io.algafoodapi.api.dto.response.RestauranteDtoResponse;
import io.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
}

