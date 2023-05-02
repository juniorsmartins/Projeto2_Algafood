package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.model.Permissao;
import io.algafoodapi.business.service.PoliticaCrudBaseService;
import io.algafoodapi.presentation.dto.request.PermissaoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.PermissaoDtoRequest;
import io.algafoodapi.presentation.dto.request.PermissaoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.PermissaoDtoResponse;
import io.algafoodapi.presentation.dto.response.PermissaoResumoDtoResponse;
import io.algafoodapi.presentation.mapper.PoliticaMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/permissoes")
public final class PermissaoControllerImpl implements PoliticaCrudBaseController<PermissaoDtoRequest, PermissaoDtoResponse,
    PermissaoPesquisarDtoRequest, PermissaoAtualizarDtoRequest, Long> {

  @Autowired
  private PoliticaMapper<PermissaoDtoRequest, PermissaoDtoResponse, PermissaoPesquisarDtoRequest,
      PermissaoAtualizarDtoRequest, PermissaoResumoDtoResponse, Permissao, Long> mapper;

  @Autowired
  private PoliticaCrudBaseService<Permissao, Long> service;

  @Override
  public ResponseEntity<PermissaoDtoResponse> cadastrar(@RequestBody @Valid final PermissaoDtoRequest dtoRequest,
                                                        final UriComponentsBuilder uriComponentsBuilder) {

    var response = Optional.of(dtoRequest)
        .map(this.mapper::converterDtoRequestParaEntidade)
        .map(this.service::cadastrar)
        .map(this.mapper::converterEntidadeParaDtoResponse)
        .orElseThrow();

    return ResponseEntity
        .created(uriComponentsBuilder
            .path("/v1/permissoes/{id}")
            .buildAndExpand(response.getId())
            .toUri())
        .body(response);
  }

  @Override
  public ResponseEntity<PermissaoDtoResponse> atualizar(@RequestBody @Valid PermissaoAtualizarDtoRequest dtoRequest) {

    var response = Optional.of(dtoRequest)
        .map(this.mapper::converterAtualizarDtoRequestParaEntidade)
        .map(this.service::atualizar)
        .map(this.mapper::converterEntidadeParaDtoResponse)
        .orElseThrow();

    return ResponseEntity
        .ok()
        .body(response);
  }

  @Override
  public ResponseEntity<Page<PermissaoDtoResponse>> pesquisar(final PermissaoPesquisarDtoRequest dtoRequest,
    @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) final Pageable paginacao) {

    var response = Optional.of(dtoRequest)
        .map(this.mapper::converterPesquisarDtoRequestParaEntidade)
        .map(permissao -> this.service.pesquisar(permissao, paginacao))
        .map(this.mapper::converterPaginaDeEntidadesParaPaginaDeDtoResponse)
        .orElseThrow();

    return ResponseEntity
        .ok()
        .body(response);
  }

  @Override
  public ResponseEntity deletar(@PathVariable(name = "id") final Long id) {

    this.service.deletar(id);

    return ResponseEntity
        .noContent()
        .build();
  }
}

