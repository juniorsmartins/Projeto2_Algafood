package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.EstadoDtoRequest;
import io.algafoodapi.presentation.dto.response.EstadoDtoResponse;
import io.algafoodapi.presentation.mapper.EstadoMapper;
import io.algafoodapi.business.service.EstadoService;
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

import jakarta.validation.Valid;
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
            .map(this.estadoMapper::converterDtoRequestParaEntidade)
            .map(this.estadoService::criar)
            .map(this.estadoMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .created(uriComponentsBuilder
                .path("estados/{id}")
                .buildAndExpand(response.getId())
                .toUri())
            .body(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EstadoDtoResponse> atualizar(@PathVariable(name = "id") final Long idEstado,
                                       @RequestBody @Valid final EstadoDtoRequest estadoDtoRequest) {

        var response = Optional.of(estadoDtoRequest)
            .map(this.estadoMapper::converterDtoRequestParaEntidade)
            .map(state -> this.estadoService.atualizar(idEstado, state))
            .map(this.estadoMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @DeleteMapping(path = "/{idEstado}")
    public ResponseEntity excluirPorId(@PathVariable(name = "idEstado") final Long idEstado) {

        this.estadoService.excluirPorId(idEstado);

        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping(path = "/{idEstado}")
    public ResponseEntity<EstadoDtoResponse> consultarPorId(@PathVariable(name = "idEstado") final Long idEstado) {

        var response = Optional.of(this.estadoService.consultarPorId(idEstado))
            .map(this.estadoMapper::converterEntidadeParaDtoResponse)
            .get();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<EstadoDtoResponse>> listar() {

        var estados = this.estadoService.listar()
            .stream()
            .map(this.estadoMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(estados);
    }
}

