package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

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

            var cozinha = this.cozinhaService.atualizar(id, cozinhaAtual);

            return ResponseEntity
                    .ok()
                    .body(cozinha);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "id") Long id) {

            this.cozinhaService.excluirPorId(id);

            return ResponseEntity
                    .noContent()
                    .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "id") Long id) {

            var cozinha = this.cozinhaService.consultarPorIdOuLancarException(id);

            return ResponseEntity
                    .ok()
                    .body(cozinha);
    }

    @GetMapping(value = "/porNome")
    public ResponseEntity<?> consultarPorNome(@RequestParam(name = "estilo_de_comida") String nome) {

        try {
            var cozinhas = this.cozinhaService.consultarPorNome(nome);
            return ResponseEntity
                    .ok()
                    .body(cozinhas);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        try {
            var cozinhas = this.cozinhaService.buscarTodos();
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
