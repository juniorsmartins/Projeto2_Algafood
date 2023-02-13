package io.algafoodapi.api.controller;

import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid Cidade cidadeNova, UriComponentsBuilder uriComponentsBuilder) {

        cidadeNova = this.cidadeService.criar(cidadeNova);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("cidades/{id}")
                        .buildAndExpand(cidadeNova.getId())
                        .toUri())
                .body(cidadeNova);
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

