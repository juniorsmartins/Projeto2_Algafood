package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Cidade cidade, UriComponentsBuilder uriComponentsBuilder) {

        cidade = this.cidadeService.criar(cidade);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("cidades/{id}")
                        .buildAndExpand(cidade.getId())
                        .toUri())
                .body(cidade);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long id, @RequestBody Cidade cidade) {

        cidade = this.cidadeService.atualizar(id, cidade);

        return ResponseEntity
                .ok()
                .body(cidade);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "id") Long id) {

        this.cidadeService.excluirPorId(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "id") Long id) {

        var cidade = this.cidadeService.consultarPorId(id);

        return ResponseEntity
                .ok()
                .body(cidade);
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        try {
            var cidades = this.cidadeService.buscarTodos();
            return ResponseEntity
                    .ok()
                    .body(cidades);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
}
