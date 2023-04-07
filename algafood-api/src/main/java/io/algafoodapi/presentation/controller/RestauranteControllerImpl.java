package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.business.model.Produto;
import io.algafoodapi.business.model.Restaurante;
import io.algafoodapi.business.service.PoliticaCrudBaseService;
import io.algafoodapi.business.service.PoliticaRestauranteService;
import io.algafoodapi.presentation.dto.request.FormaPagamentoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.presentation.dto.request.FormaPagamentoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.request.ProdutoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.ProdutoDtoRequest;
import io.algafoodapi.presentation.dto.request.ProdutoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.request.RestauranteAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.RestauranteDtoRequest;
import io.algafoodapi.presentation.dto.request.RestaurantePesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.presentation.dto.response.ProdutoDtoResponse;
import io.algafoodapi.presentation.dto.response.RestauranteDtoResponse;
import io.algafoodapi.presentation.mapper.PoliticaMapper;
import io.algafoodapi.presentation.mapper.RestauranteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/restaurantes")
public final class RestauranteControllerImpl implements PoliticaCrudBaseController<RestauranteDtoRequest, RestauranteDtoResponse,
    RestaurantePesquisarDtoRequest, RestauranteAtualizarDtoRequest, Long>, PoliticaRestauranteController<RestauranteDtoRequest,
    RestauranteDtoResponse, Long> {

    @Autowired
    private RestauranteMapper mapper;

    @Autowired
    private PoliticaMapper<FormaPagamentoDtoRequest, FormaPagamentoDtoResponse, FormaPagamentoPesquisarDtoRequest,
        FormaPagamentoAtualizarDtoRequest, FormaPagamento, Long> formaPagamentoMapper;

    @Autowired
    private PoliticaMapper<ProdutoDtoRequest, ProdutoDtoResponse, ProdutoPesquisarDtoRequest,
        ProdutoAtualizarDtoRequest, Produto, Long> produtoMapper;

    @Autowired
    private PoliticaCrudBaseService<Restaurante, Long> crudService;

    @Autowired
    private PoliticaRestauranteService<Restaurante, Long> restauranteService;

    @Override
    public ResponseEntity<RestauranteDtoResponse> cadastrar(@RequestBody @Valid final RestauranteDtoRequest restauranteDtoRequest,
                                                            final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(restauranteDtoRequest)
            .map(this.mapper::converterDtoRequestParaEntidade)
            .map(this.crudService::cadastrar)
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .created(uriComponentsBuilder
                .path("/v1/restaurantes/{id}")
                .buildAndExpand(response.getId())
                .toUri())
            .body(response);
    }

    @Override
    public ResponseEntity<RestauranteDtoResponse> atualizar(@RequestBody @Valid final RestauranteAtualizarDtoRequest dtoRequest) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterAtualizarDtoRequestParaEntidade)
            .map(restaurant -> this.crudService.atualizar(restaurant))
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<Page<RestauranteDtoResponse>> pesquisar(final RestaurantePesquisarDtoRequest dtoRequest,
        @PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 20) final Pageable paginacao) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterPesquisarDtoRequestParaEntidade)
            .map(entidade -> this.crudService.pesquisar(entidade, paginacao))
            .map(this.mapper::converterPaginaDeRestaurantesParaPaginaDeDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity deletar(@PathVariable(name = "id") final Long id) {

        this.crudService.deletar(id);

        return ResponseEntity
            .noContent()
            .build();
    }

    @Override
    public ResponseEntity<RestauranteDtoResponse> atualizarParcial(@PathVariable(name = "id") final Long id,
                                  @RequestBody Map<String, Object> campos, final HttpServletRequest request) {

        var response = Optional.of(this.restauranteService.atualizarParcial(id, campos, request))
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<RestauranteDtoResponse> consultarPorId(@PathVariable(name = "id") final Long id) {

        var response = Optional.of(this.restauranteService.consultarPorId(id))
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<List<RestauranteDtoResponse>> consultarPorNome(@RequestParam(name = "nome") final String nome) {

        var response = this.restauranteService.consultarPorNome(nome)
            .stream()
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<List<RestauranteDtoResponse>> listar() {

        var response = this.restauranteService.listar()
            .stream()
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }

//    @Override
//    public ResponseEntity<List<RestauranteDtoResponse>> consultarPorNomeAndTaxas(@Param("nome") final String nome,
//        @Param("freteTaxaInicial") final BigDecimal freteTaxaInicial, @Param("freteTaxaFinal") final BigDecimal freteTaxaFinal) {
//
//        var response = this.restauranteService.consultarPorNomeAndTaxas(nome, freteTaxaInicial, freteTaxaFinal)
//            .stream()
//            .map(this.mapper::converterEntidadeParaDtoResponse)
//            .toList();
//
//        return ResponseEntity
//            .ok()
//            .body(response);
//    }

    @Override
    public ResponseEntity ativar(@PathVariable(name = "id") final Long idRestaurante) {

        this.restauranteService.ativar(idRestaurante);

        return ResponseEntity
            .noContent()
            .build();
    }

    @Override
    public ResponseEntity inativar(@PathVariable(name = "id") final Long idRestaurante) {

        this.restauranteService.inativar(idRestaurante);

        return ResponseEntity
            .noContent()
            .build();
    }

    @Override
    public ResponseEntity<List<FormaPagamentoDtoResponse>> consultarFormasDePagamentoPorRestaurante(@PathVariable(name = "id") final Long id) {

        var response = this.restauranteService.consultarFormasDePagamentoPorRestaurante(id)
            .stream()
            .map(this.formaPagamentoMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<ProdutoDtoResponse> cadastrarProdutoPorRestaurante(@PathVariable(name = "id") final Long id,
                                                             @RequestBody @Valid final ProdutoDtoRequest dtoRequest) {

        var response = Optional.of(dtoRequest)
            .map(this.produtoMapper::converterDtoRequestParaEntidade)
            .map(produto -> this.restauranteService.cadastrarProdutoPorRestaurante(id, produto))
            .map(this.produtoMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }


    @Override
    public ResponseEntity<List<ProdutoDtoResponse>> consultarProdutosPorRestaurante(@PathVariable(name = "id") final Long id) {

        var response = this.restauranteService.consultarProdutosPorRestaurante(id)
            .stream()
            .map(this.produtoMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }


}

