package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.service.GrupoService;
import io.algafoodapi.presentation.dto.request.GrupoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.GrupoDtoRequest;
import io.algafoodapi.presentation.dto.request.GrupoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.GrupoDtoResponse;
import io.algafoodapi.presentation.dto.response.PermissaoDtoResponse;
import io.algafoodapi.presentation.mapper.GrupoMapper;
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
import java.util.Set;

@RestController
@RequestMapping(path = "/v1/grupos")
public final class GrupoController implements PoliticaCrudBaseController<GrupoDtoRequest, GrupoDtoResponse,
    GrupoPesquisarDtoRequest, GrupoAtualizarDtoRequest, Long>, PoliticaGrupoController {

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private GrupoService grupoService;

    @Override
    public ResponseEntity<GrupoDtoResponse> cadastrar(@RequestBody @Valid final GrupoDtoRequest dtoRequest,
                                                      final UriComponentsBuilder uriComponentsBuilder) {
        var response = Optional.of(dtoRequest)
            .map(this.grupoMapper::converterDtoRequestParaEntidade)
            .map(this.grupoService::cadastrar)
            .map(this.grupoMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .created(uriComponentsBuilder
                    .path("/v1/grupos/{id}")
                    .buildAndExpand(response.getId())
                    .toUri())
            .body(response);
    }

    @Override
    public ResponseEntity<GrupoDtoResponse> atualizar(@RequestBody @Valid final GrupoAtualizarDtoRequest atualizarDtoRequest) {

        var response = Optional.of(atualizarDtoRequest)
            .map(this.grupoMapper::converterAtualizarDtoRequestParaEntidade)
            .map(this.grupoService::atualizar)
            .map(this.grupoMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<Page<GrupoDtoResponse>> pesquisar(final GrupoPesquisarDtoRequest pesquisarDtoRequest,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) final Pageable paginacao) {

        var response = Optional.of(pesquisarDtoRequest)
            .map(this.grupoMapper::converterPesquisarDtoRequestParaEntidade)
            .map(grupo -> this.grupoService.pesquisar(grupo, paginacao))
            .map(this.grupoMapper::converterPaginaDeEntidadeParaPaginaDeDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity deletar(@PathVariable(name = "id") final Long id) {

        this.grupoService.apagarPorId(id);

        return ResponseEntity
            .noContent()
            .build();
    }

    @Override
    public ResponseEntity<Set<PermissaoDtoResponse>> consultarPermissoesPorIdDeGrupo(@PathVariable(name = "id") final Long id) {

        return null;
    }
}

