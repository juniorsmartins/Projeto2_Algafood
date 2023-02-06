package io.algafoodapi.api.controller;

import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Cidade cidadeNova, UriComponentsBuilder uriComponentsBuilder) {

        cidadeNova = this.cidadeService.criar(cidadeNova);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("cidades/{id}")
                        .buildAndExpand(cidadeNova.getId())
                        .toUri())
                .body(cidadeNova);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long id, @RequestBody Cidade cidadeAtual) {

        cidadeAtual = this.cidadeService.atualizar(id, cidadeAtual);

        return ResponseEntity
                .ok()
                .body(cidadeAtual);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "id") Long id) {

        this.cidadeService.excluirPorId(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "id") Long id) {

        var cidade = this.cidadeService.consultarPorId(id);

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

