package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable(name = "id") Long id) {

        try {
            this.estadoService.excluir(id);
            return ResponseEntity
                    .noContent()
                    .build();

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());

        } catch (EntidadeEmUsoException emUsoException) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(emUsoException.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        try {
            var estados = this.estadoService.listar();
            return ResponseEntity
                    .ok()
                    .body(estados);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscar(@PathVariable(name = "id") Long id) {

        try {
            var estado = this.estadoService.buscar(id);
            return ResponseEntity
                    .ok()
                    .body(estado);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }
}
