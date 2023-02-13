package io.algafoodapi.api.controller;

import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

    @PostMapping
    public ResponseEntity<Cozinha> criar(@RequestBody @Valid Cozinha cozinha, UriComponentsBuilder uriComponentsBuilder) {

        cozinha = this.cozinhaService.criar(cozinha);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("cozinhas/{id}")
                        .buildAndExpand(cozinha.getId())
                        .toUri())
                .body(cozinha);
    }

    @PutMapping(path = "/{cozinhaId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "cozinhaId") Long cozinhaId, @RequestBody @Valid Cozinha cozinhaAtual) {

        cozinhaAtual = this.cozinhaService.atualizar(cozinhaId, cozinhaAtual);

        return ResponseEntity
                .ok()
                .body(cozinhaAtual);
    }

    @DeleteMapping(path = "/{cozinhaId}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "cozinhaId") Long cozinhaId) {

        this.cozinhaService.excluirPorId(cozinhaId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{cozinhaId}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "cozinhaId") Long cozinhaId) {

        var cozinha = this.cozinhaService.consultarPorId(cozinhaId);

        return ResponseEntity
                .ok()
                .body(cozinha);
    }

    @GetMapping(path = "/por-nome")
    public ResponseEntity<?> consultarPorNome(@RequestParam(name = "gastronomia") String nome) {

        var cozinhas = this.cozinhaService.consultarPorNome(nome);

        return ResponseEntity
                .ok()
                .body(cozinhas);
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        var cozinhas = this.cozinhaService.listar();

        return ResponseEntity
                .ok()
                .body(cozinhas);
    }
}

