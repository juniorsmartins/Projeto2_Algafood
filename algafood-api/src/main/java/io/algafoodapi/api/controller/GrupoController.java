package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.GrupoDtoRequest;
import io.algafoodapi.api.dto.response.GrupoDtoResponse;
import io.algafoodapi.api.mapper.GrupoMapper;
import io.algafoodapi.domain.service.GrupoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/grupos")
public final class GrupoController {

    private final GrupoMapper grupoMapper;

    private final GrupoService grupoService;

    public GrupoController(final GrupoMapper grupoMapper, final GrupoService grupoService) {
        this.grupoMapper = grupoMapper;
        this.grupoService = grupoService;
    }

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
}

