package io.algafoodapi.presentation.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.business.service.PoliticaCrudBaseService;
import io.algafoodapi.business.service.PoliticaPedidoService;
import io.algafoodapi.presentation.dto.request.PedidoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.PedidoDtoRequest;
import io.algafoodapi.presentation.dto.request.PedidoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.PedidoDtoResponse;
import io.algafoodapi.presentation.dto.response.PedidoResumoDtoResponse;
import io.algafoodapi.presentation.filtros.PedidoFiltro;
import io.algafoodapi.presentation.mapper.PoliticaMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/pedidos")
public final class PedidoControllerImpl implements PoliticaCrudBaseController<PedidoDtoRequest, PedidoDtoResponse,
    PedidoPesquisarDtoRequest, PedidoAtualizarDtoRequest, Long>, PoliticaPedidoController<PedidoDtoRequest, PedidoDtoResponse, Long> {

  @Autowired
  private PoliticaMapper<PedidoDtoRequest, PedidoDtoResponse, PedidoPesquisarDtoRequest,
      PedidoAtualizarDtoRequest, PedidoResumoDtoResponse, Pedido, Long> mapper;

  @Autowired
  private PoliticaCrudBaseService<Pedido, Long> pedidoCrudService;

  @Autowired
  private PoliticaPedidoService service;

  @Override
  public MappingJacksonValue listarCamposEscolhidos(@RequestParam(required = false) final String campos) {

    var pedidosDtoResponse = this.service.listar();
    MappingJacksonValue pedidosEnvelopados = new MappingJacksonValue(pedidosDtoResponse);

    SimpleFilterProvider provedorDeFiltro = new SimpleFilterProvider();
    provedorDeFiltro.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

    if (campos != null && !campos.isBlank()) {
      provedorDeFiltro.addFilter("pedidoFilter", SimpleBeanPropertyFilter
          .filterOutAllExcept(campos.split(",")));
    }

    pedidosEnvelopados.setFilters(provedorDeFiltro);

    return pedidosEnvelopados;
  }

  @Override
  public ResponseEntity<PedidoDtoResponse> cadastrar(
      @RequestBody @Valid final PedidoDtoRequest dtoRequest, final UriComponentsBuilder uriComponentsBuilder) {

    var response = Optional.of(dtoRequest)
        .map(this.mapper::converterDtoRequestParaEntidade)
        .map(this.pedidoCrudService::cadastrar)
        .map(this.mapper::converterEntidadeParaDtoResponse)
        .orElseThrow();

    return ResponseEntity
        .created(uriComponentsBuilder
            .path("/v1/pedidos/{id}")
            .buildAndExpand(response.getId())
            .toUri())
        .body(response);
  }

  @Override
  public ResponseEntity<PedidoDtoResponse> atualizar(PedidoAtualizarDtoRequest dtoRequest) {
    return null;
  }

  @Override
  public ResponseEntity<Page<PedidoDtoResponse>> pesquisar(final PedidoPesquisarDtoRequest dtoRequest,
   @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) final Pageable paginacao) {

    var response = Optional.of(dtoRequest)
        .map(this.mapper::converterPesquisarDtoRequestParaEntidade)
        .map(pedido -> this.pedidoCrudService.pesquisar(pedido, paginacao))
        .map(this.mapper::converterPaginaDeEntidadesParaPaginaDeDtoResponse)
        .orElseThrow();

    return ResponseEntity
        .ok()
        .body(response);
  }

  @Override
  public ResponseEntity deletar(@PathVariable(name = "id") final Long id) {

    this.pedidoCrudService.deletar(id);

    return ResponseEntity
        .noContent()
        .build();
  }

  @Override
  public ResponseEntity<List<PedidoResumoDtoResponse>> listarResumido() {

    var response = this.service.listar()
        .stream()
        .map(this.mapper::converterEntidadeParaResumoDtoResponse)
        .toList();

    return ResponseEntity
        .ok()
        .body(response);
  }

  // TODO - cadastrar pedidos com bug

  @Override
  public ResponseEntity<Page<PedidoDtoResponse>> pesquisarComFiltro(
    final PedidoFiltro pedidoFiltro,
    @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao) {

    var responsePage = Optional.of(pedidoFiltro)
      .map(filtro ->this.service.pesquisar(filtro, paginacao))
      .map(this.mapper::converterPaginaDeEntidadesParaPaginaDeDtoResponse)
      .orElseThrow();

    return ResponseEntity
      .ok()
      .body(responsePage);
  }
}

