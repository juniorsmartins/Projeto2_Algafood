package io.algafoodapi.api.controller;

import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @PostMapping
    public ResponseEntity<Estado> criar(@RequestBody Estado estado, UriComponentsBuilder uriComponentsBuilder) {

        estado = this.estadoService.salvar(estado);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("estados/{id}")
                        .buildAndExpand(estado.getId())
                        .toUri())
                .body(estado);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long id, @RequestBody Estado estadoAtual) {

        estadoAtual = this.estadoService.atualizar(id, estadoAtual);

        return ResponseEntity
                .ok()
                .body(estadoAtual);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "id") Long id) {

        this.estadoService.excluirPorId(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "id") Long id) {

        var estado = this.estadoService.consultarPorId(id);

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
