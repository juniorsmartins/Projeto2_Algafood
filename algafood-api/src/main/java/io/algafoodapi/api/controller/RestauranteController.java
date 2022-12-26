package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {

        return ResponseEntity
                .ok()
                .body(this.restauranteService.listar());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable(name = "id") Long id) {

        Restaurante restaurante;

        try {
            restaurante = this.restauranteService.buscar(id);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(restaurante);
    }
}
