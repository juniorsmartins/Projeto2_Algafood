package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private CadastroEstadoService estadoService;

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado, UriComponentsBuilder uriComponentsBuilder) {

        estado = this.estadoService.salvar(estado);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("estados/{id}")
                        .buildAndExpand(estado.getId())
                        .toUri())
                .body(estado);
    }

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {

        try {
            var estados = this.estadoService.listar();
            return ResponseEntity
                    .ok()
                    .body(estados);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable(name = "id") Long id) {

        try {
            var estado = this.estadoService.buscar(id);
            return ResponseEntity
                    .ok()
                    .body(estado);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
