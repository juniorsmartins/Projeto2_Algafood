package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.RestauranteDtoRequest;
import io.algafoodapi.api.dto.response.RestauranteDtoResponse;
import io.algafoodapi.api.mapper.RestauranteMapper;
import io.algafoodapi.domain.service.RestauranteService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/restaurantes")
public final class RestauranteController {

    private final RestauranteMapper restauranteMapper;
    private final RestauranteService restauranteService;

    public RestauranteController(final RestauranteMapper restauranteMapper, final RestauranteService restauranteService) {
        this.restauranteMapper = restauranteMapper;
        this.restauranteService = restauranteService;
    }

    @PostMapping
    public ResponseEntity<RestauranteDtoResponse> criar(@RequestBody @Valid final RestauranteDtoRequest restauranteDtoRequest,
                                                        final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(restauranteDtoRequest)
            .map(this.restauranteMapper::converterDtoRequestParaEntidade)
            .map(this.restauranteService::criar)
            .map(this.restauranteMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .created(uriComponentsBuilder
                .path("/v1/restaurantes/{id}")
                .buildAndExpand(response.getId())
                .toUri())
            .body(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RestauranteDtoResponse> atualizar(@PathVariable(name = "id") final Long idRestaurante,
                       @RequestBody @Valid final RestauranteDtoRequest restauranteDtoRequest) {

        var response = Optional.of(restauranteDtoRequest)
            .map(this.restauranteMapper::converterDtoRequestParaEntidade)
            .map(restaurant -> this.restauranteService.atualizar(idRestaurante, restaurant))
            .map(this.restauranteMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RestauranteDtoResponse> atualizarParcial(@PathVariable(name = "id") final Long idRestaurante,
                                              @RequestBody Map<String, Object> campos,
                                              final HttpServletRequest request) {

        var response = Optional.of(this.restauranteService.atualizarParcial(
            idRestaurante, campos, request))
            .map(this.restauranteMapper::converterEntidadeParaDtoResponse)
            .get();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity excluirPorId(@PathVariable(name = "id") final Long idRestaurante) {

        this.restauranteService.excluirPorId(idRestaurante);

        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RestauranteDtoResponse> consultarPorId(@PathVariable(name = "id") final Long idRestaurante) {

        var response = Optional.of(this.restauranteService.consultarPorId(idRestaurante))
            .map(this.restauranteMapper::converterEntidadeParaDtoResponse)
            .get();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping(path = "/por-nome")
    public ResponseEntity<List<RestauranteDtoResponse>> consultarPorNome(@RequestParam(name = "nome") final String nome) {

        var response = this.restauranteService.consultarPorNome(nome)
            .stream()
            .map(this.restauranteMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<RestauranteDtoResponse>> listar() {

        var response = this.restauranteService.listar()
            .stream()
            .map(this.restauranteMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping(path = "/por-nome-e-taxas")
    public ResponseEntity<List<RestauranteDtoResponse>> consultarPorNomeAndTaxas(@Param("nome") final String nome,
                                                      @Param("freteTaxaInicial") final BigDecimal freteTaxaInicial,
                                                      @Param("freteTaxaFinal") final BigDecimal freteTaxaFinal) {

        var response = this.restauranteService.consultarPorNomeAndTaxas(nome, freteTaxaInicial, freteTaxaFinal)
            .stream()
            .map(this.restauranteMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @PutMapping(path = "/{id}/ativos")
    public ResponseEntity ativar(@PathVariable(name = "id") final Long idRestaurante) {
        this.restauranteService.ativar(idRestaurante);

        return ResponseEntity
            .noContent()
            .build();
    }

    @DeleteMapping(path = "/{id}/ativos")
    public ResponseEntity inativar(@PathVariable(name = "id") final Long idRestaurante) {
        this.restauranteService.inativar(idRestaurante);

        return ResponseEntity
            .noContent()
            .build();
    }
}

