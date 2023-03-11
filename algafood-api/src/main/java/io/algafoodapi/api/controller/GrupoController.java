package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.GrupoAtualizarDtoRequest;
import io.algafoodapi.api.dto.request.GrupoDtoRequest;
import io.algafoodapi.api.dto.request.GrupoPesquisarDtoRequest;
import io.algafoodapi.api.dto.response.GrupoDtoResponse;
import io.algafoodapi.api.mapper.GrupoMapper;
import io.algafoodapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/grupos")
public final class GrupoController {

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private GrupoService grupoService;

    @PostMapping
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

    @PutMapping
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

    @GetMapping
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity apagarPorId(@PathVariable(name = "id") final Long idGrupo) {

        this.grupoService.apagarPorId(idGrupo);

        return ResponseEntity
            .noContent()
            .build();
    }



}

