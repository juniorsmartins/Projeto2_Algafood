package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Prato;
import io.algafoodapi.domain.service.PratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/pratos", produces = {"application/json"})
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @PostMapping
    public ResponseEntity<Prato> create(@RequestBody Prato prato, UriComponentsBuilder uriComponentsBuilder) {

        var pratoCriado = this.pratoService.create(prato);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("pratos/{id}")
                        .buildAndExpand(pratoCriado.getId())
                        .toUri())
                .body(pratoCriado);
    }

    @GetMapping(path = "/pratosGratisComNomeSemelhante")
    public ResponseEntity<List<Prato>> pratosGratisComNomeSemelhante(@RequestParam(name = "nome") String nome) {

        try {
            var pratos = this.pratoService.pratosGratisComNomeSemelhante(nome);
            return ResponseEntity
                    .ok()
                    .body(pratos);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @GetMapping(path = "/pratoPrimeiro")
    public ResponseEntity<?> pratoPrimeiro() {

        try {
            var prato = this.pratoService.pratoPrimeiro();
            return ResponseEntity
                    .ok()
                    .body(prato.get());

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
}

