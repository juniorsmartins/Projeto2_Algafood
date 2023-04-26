package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.model.Grupo;
import io.algafoodapi.business.model.Permissao;
import io.algafoodapi.business.service.PoliticaCrudBaseService;
import io.algafoodapi.business.service.PoliticaGrupoService;
import io.algafoodapi.presentation.dto.request.GrupoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.GrupoDtoRequest;
import io.algafoodapi.presentation.dto.request.GrupoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.request.PermissaoAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.PermissaoDtoRequest;
import io.algafoodapi.presentation.dto.request.PermissaoPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.GrupoDtoResponse;
import io.algafoodapi.presentation.dto.response.PermissaoDtoResponse;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/grupos")
public final class GrupoControllerImpl implements PoliticaCrudBaseController<GrupoDtoRequest, GrupoDtoResponse,
    GrupoPesquisarDtoRequest, GrupoAtualizarDtoRequest, Long>, PoliticaGrupoController<GrupoDtoRequest, GrupoDtoResponse, Long> {

    @Autowired
    private PoliticaMapper<GrupoDtoRequest, GrupoDtoResponse, GrupoPesquisarDtoRequest,
        GrupoAtualizarDtoRequest, Grupo, Long> mapper;

    @Autowired
    private PoliticaMapper<PermissaoDtoRequest, PermissaoDtoResponse, PermissaoPesquisarDtoRequest,
        PermissaoAtualizarDtoRequest, Permissao, Long> permissaoMapper;

    @Autowired
    private PoliticaCrudBaseService<Grupo, Long> service;

    @Autowired
    private PoliticaGrupoService<Long> grupoService;

    @Override
    public ResponseEntity<GrupoDtoResponse> cadastrar(@RequestBody @Valid final GrupoDtoRequest dtoRequest,
                                                      final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterDtoRequestParaEntidade)
            .map(this.service::cadastrar)
            .map(this.mapper::converterEntidadeParaDtoResponse)
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
            .map(this.mapper::converterAtualizarDtoRequestParaEntidade)
            .map(this.service::atualizar)
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<Page<GrupoDtoResponse>> pesquisar(final GrupoPesquisarDtoRequest pesquisarDtoRequest,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) final Pageable paginacao) {

        var response = Optional.of(pesquisarDtoRequest)
            .map(this.mapper::converterPesquisarDtoRequestParaEntidade)
            .map(grupo -> this.service.pesquisar(grupo, paginacao))
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

    @Override
    public ResponseEntity<Set<PermissaoDtoResponse>> consultarPermissoesPorIdDeGrupo(@PathVariable(name = "id") final Long id) {

        var response = this.grupoService.consultarPermissoesPorIdDeGrupo(id)
            .stream()
            .map(this.permissaoMapper::converterEntidadeParaDtoResponse)
            .collect(Collectors.toSet());

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<GrupoDtoResponse> associarPermissaoNoGrupoPorIds(Long idGrupo, Long idPermissao) {
        return null;
    }
}

