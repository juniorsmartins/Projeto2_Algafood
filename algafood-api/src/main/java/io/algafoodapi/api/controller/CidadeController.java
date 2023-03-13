package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.CidadeDtoRequest;
import io.algafoodapi.api.dto.response.CidadeDtoResponse;
import io.algafoodapi.api.mapper.CidadeMapper;
import io.algafoodapi.domain.service.CidadeService;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/cidades")
public final class CidadeController {

    private final CidadeMapper cidadeMapper;

    private final CidadeService cidadeService;

    public CidadeController(final CidadeMapper cidadeMapper, final CidadeService cidadeService) {
        this.cidadeMapper = cidadeMapper;
        this.cidadeService = cidadeService;
    }

    @PostMapping
    public ResponseEntity<CidadeDtoResponse> criar(@RequestBody @Valid final CidadeDtoRequest cidadeDtoRequest,
                                                   final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(cidadeDtoRequest)
                .map(this.cidadeMapper::converterDtoRequestParaEntidade)
                .map(this.cidadeService::criar)
                .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
                .orElseThrow();

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/v1/cidades/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @PutMapping(path = "/{idCidade}")
    public ResponseEntity<CidadeDtoResponse> atualizar(@PathVariable(name = "idCidade") final Long idCidade,
                                       @RequestBody @Valid final CidadeDtoRequest cidadeDtoRequest) {

        var response = Optional.of(cidadeDtoRequest)
                .map(this.cidadeMapper::converterDtoRequestParaEntidade)
                .map(city -> this.cidadeService.atualizar(idCidade, city))
                .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
                .orElseThrow();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping(path = "/{idCidade}")
    public ResponseEntity excluirPorId(@PathVariable(name = "idCidade") final Long idCidade) {

        this.cidadeService.excluirPorId(idCidade);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{idCidade}")
    public ResponseEntity<CidadeDtoResponse> consultarPorId(@PathVariable(name = "idCidade") final Long idCidade) {

        var response = Optional.of(this.cidadeService.consultarPorId(idCidade))
                .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
                .get();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CidadeDtoResponse>> listar() {

        var cidades = this.cidadeService.listar()
                .stream()
                .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
                .toList();

        return ResponseEntity
                .ok()
                .body(cidades);
    }
}

