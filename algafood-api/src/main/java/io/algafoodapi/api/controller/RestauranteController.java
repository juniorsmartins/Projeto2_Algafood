package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {

        return ResponseEntity
                .ok()
                .body(this.restauranteService.listar());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable(name = "id") Long id) {

        try {
            var restaurante = this.restauranteService.buscar(id);
            return ResponseEntity
                    .ok()
                    .body(restaurante);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
