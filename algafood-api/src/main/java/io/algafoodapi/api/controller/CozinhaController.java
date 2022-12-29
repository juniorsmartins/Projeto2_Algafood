package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha, UriComponentsBuilder uriComponentsBuilder) {

        cozinha = this.cozinhaService.salvar(cozinha);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("cozinhas/{id}")
                        .buildAndExpand(cozinha.getId())
                        .toUri())
                .body(cozinha);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long id, @RequestBody Cozinha cozinhaAtual) {

        try {
            var cozinha = this.cozinhaService.atualizar(id, cozinhaAtual);
            return ResponseEntity
                    .ok()
                    .body(cozinha);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable(name = "id") Long id) {

        try {
            this.cozinhaService.excluir(id);
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscar(@PathVariable(name = "id") Long id) {

        try {
            var cozinha = this.cozinhaService.buscar(id);
            return ResponseEntity
                    .ok()
                    .body(cozinha);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        try {
            var cozinhas = this.cozinhaService.listar();
            return ResponseEntity
                    .ok()
                    .body(cozinhas);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
}
