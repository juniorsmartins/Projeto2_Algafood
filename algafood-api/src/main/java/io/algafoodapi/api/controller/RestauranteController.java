package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante, UriComponentsBuilder uriComponentsBuilder) {

        try {
            restaurante = this.restauranteService.salvar(restaurante);
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .path("restaurantes/{id}")
                            .buildAndExpand(restaurante.getId())
                            .toUri())
                    .body(restaurante);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .badRequest()
                    .body(naoEncontradaException.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long id, @RequestBody Restaurante restaurante) {
        try {
            restaurante = this.restauranteService.atualizar(id, restaurante);
            return ResponseEntity
                    .ok()
                    .body(restaurante);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());

        } catch (RequisicaoMalFormuladaException malFormuladaException) {
            return ResponseEntity
                    .badRequest()
                    .body(malFormuladaException.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable(name = "id") Long id) {

        try {
            this.restauranteService.excluir(id);
            return ResponseEntity
                    .noContent()
                    .build();

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscar(@PathVariable(name = "id") Long id) {

        try {
            var restaurante = this.restauranteService.buscar(id);
            return ResponseEntity
                    .ok()
                    .body(restaurante);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        try {
            var restaurantes = this.restauranteService.listar();
            return ResponseEntity
                    .ok()
                    .body(restaurantes);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
}
