package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.CidadeDtoRequest;
import io.algafoodapi.api.dto.response.CidadeDtoResponse;
import io.algafoodapi.domain.core.mapper.CidadeMapper;
import io.algafoodapi.domain.model.Cidade;
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
                        .path("cidades/{id}")
                        .buildAndExpand(response.id())
                        .toUri())
                .body(response);
    }

    @PutMapping(path = "/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "cidadeId") Long cidadeId, @RequestBody @Valid Cidade cidadeAtual) {

        cidadeAtual = this.cidadeService.atualizar(cidadeId, cidadeAtual);

        return ResponseEntity
                .ok()
                .body(cidadeAtual);
    }

    @DeleteMapping(path = "/{cidadeId}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "cidadeId") Long cidadeId) {

        this.cidadeService.excluirPorId(cidadeId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{cidadeId}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "cidadeId") Long cidadeId) {

        var cidade = this.cidadeService.consultarPorId(cidadeId);

        return ResponseEntity
                .ok()
                .body(cidade);
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        var cidades = this.cidadeService.listar();

        return ResponseEntity
                .ok()
                .body(cidades);
    }
}

