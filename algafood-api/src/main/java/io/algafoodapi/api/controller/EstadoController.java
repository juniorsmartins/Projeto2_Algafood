package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.EstadoDtoRequest;
import io.algafoodapi.api.dto.response.EstadoDtoResponse;
import io.algafoodapi.domain.core.mapper.EstadoMapper;
import io.algafoodapi.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/v1/estados")
public final class EstadoController {

    @Autowired
    private EstadoMapper estadoMapper;

    @Autowired
    private EstadoService estadoService;

    @PostMapping
    public ResponseEntity<EstadoDtoResponse> criar(@RequestBody @Valid final EstadoDtoRequest estadoDtoRequest,
                                                   final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(estadoDtoRequest)
                .map(this.estadoMapper::converterDTORequestParaEntidade)
                .map(this.estadoService::criar)
                .map(this.estadoMapper::converterEntidadeParaDTOResponse)
                .orElseThrow();

        return ResponseEntity
                .created(uriComponentsBuilder
                    .path("estados/{id}")
                    .buildAndExpand(response.id())
                    .toUri())
                .body(response);
    }

    @PutMapping(path = "/{estadoId}")
    public ResponseEntity<EstadoDtoResponse> atualizar(@PathVariable(name = "estadoId") final Long estadoId,
                                       @RequestBody @Valid final EstadoDtoRequest estadoDtoRequest) {

        var response = Optional.of(estadoDtoRequest)
                .map(this.estadoMapper::converterDTORequestParaEntidade)
                .map(state -> this.estadoService.atualizar(estadoId, state))
                .map(this.estadoMapper::converterEntidadeParaDTOResponse)
                .orElseThrow();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping(path = "/{estadoId}")
    public ResponseEntity excluirPorId(@PathVariable(name = "estadoId") final Long estadoId) {

        this.estadoService.excluirPorId(estadoId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{estadoId}")
    public ResponseEntity<EstadoDtoResponse> consultarPorId(@PathVariable(name = "estadoId") final Long estadoId) {

        var response = Optional.of(this.estadoService.consultarPorId(estadoId))
                .map(this.estadoMapper::converterEntidadeParaDTOResponse)
                .get();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<EstadoDtoResponse>> listar() {

        var estados = this.estadoService.listar()
                .stream()
                .map(this.estadoMapper::converterEntidadeParaDTOResponse)
                .toList();

        return ResponseEntity
                .ok()
                .body(estados);
    }
}

