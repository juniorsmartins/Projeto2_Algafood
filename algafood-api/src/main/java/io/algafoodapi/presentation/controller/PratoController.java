package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.exception.http404.RecursoNaoEncontradoException;
import io.algafoodapi.business.model.Prato;
import io.algafoodapi.business.service.PratoService;
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

        } catch (RecursoNaoEncontradoException naoEncontradaException) {
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

        } catch (RecursoNaoEncontradoException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
}

