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

//        return Restaurante.builder()
//                .nome(dto.nome())
//                .taxaFrete(dto.taxaFrete())
//                .cozinha(Cozinha.builder()
//                        .id(dto.cozinha().id())
//                        .build())
//                .endereco(Endereco.builder()
//                        .cep(dto.endereco().getCep())
//                        .cidade(Cidade.builder()
//                                .id(dto.endereco().getCidade().getId())
//                                .nome(dto.endereco().getCidade().getNome())
//                                .estado(Estado.builder()
//                                        .id(dto.endereco().getCidade().getEstado().getId())
//                                        .nome(dto.endereco().getCidade().getEstado().getNome())
//                                        .build())
//                                .build())
//                        .bairro(dto.endereco().getBairro())
//                        .logradouro(dto.endereco().getLogradouro())
//                        .numero(dto.endereco().getNumero())
//                        .complemento(dto.endereco().getComplemento())
//                        .build())
//                .build();
    }

    public RestauranteDtoResponse converterEntidadeParaDtoResponse(final Restaurante restaurante) {
        return this.modelMapper.map(restaurante, RestauranteDtoResponse.class);

//        return RestauranteDtoResponse.builder()
//                .id(restaurante.getId())
//                .nome(restaurante.getNome())
//                .taxaFrete(new BigDecimal(String.valueOf(restaurante.getTaxaFrete())))
//                .cozinha(CozinhaDtoResponse.builder()
//                        .id(restaurante.getCozinha().getId())
//                        .nome(restaurante.getCozinha().getNome())
//                        .build())
//                .endereco(Endereco.builder()
//                        .cep(restaurante.getEndereco().getCep())
//                        .cidade(Cidade.builder()
//                                .id(restaurante.getEndereco().getCidade().getId())
//                                .nome(restaurante.getEndereco().getCidade().getNome())
//                                .estado(Estado.builder()
//                                        .id(restaurante.getEndereco().getCidade().getEstado().getId())
//                                        .nome(restaurante.getEndereco().getCidade().getEstado().getNome())
//                                        .build())
//                                .build())
//                        .bairro(restaurante.getEndereco().getBairro())
//                        .logradouro(restaurante.getEndereco().getLogradouro())
//                        .numero(restaurante.getEndereco().getNumero())
//                        .complemento(restaurante.getEndereco().getComplemento())
//                        .build())
//                .dataHoraUTCCadastro(restaurante.getDataHoraUTCCadastro())
//                .dataHoraUTCAtualizacao(restaurante.getDataHoraUTCAtualizacao())
//                .build();
    }
}

