package io.algafoodapi.api.controller;

import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @PostMapping
    public ResponseEntity<Estado> criar(@RequestBody @Valid Estado estado, UriComponentsBuilder uriComponentsBuilder) {

        estado = this.estadoService.criar(estado);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("estados/{id}")
                        .buildAndExpand(estado.getId())
                        .toUri())
                .body(estado);
    }

    @PutMapping(path = "/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "estadoId") Long estadoId, @RequestBody @Valid Estado estadoAtual) {

        estadoAtual = this.estadoService.atualizar(estadoId, estadoAtual);

        return ResponseEntity
                .ok()
                .body(estadoAtual);
    }

    @DeleteMapping(path = "/{estadoId}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "estadoId") Long estadoId) {

        this.estadoService.excluirPorId(estadoId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{estadoId}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "estadoId") Long estadoId) {

        var estado = this.estadoService.consultarPorId(estadoId);

        return ResponseEntity
                .ok()
                .body(estado);
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        var estados = this.estadoService.listar();

        return ResponseEntity
                .ok()
                .body(estados);
    }
}

