package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Restaurante restaurante, UriComponentsBuilder uriComponentsBuilder) {

        try {
            restaurante = this.restauranteService.criar(restaurante);
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

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable(name = "id") Long id, @RequestBody Map<String, Object> campos) {

        try {
            var restaurante = this.restauranteService.atualizarParcial(id, campos);
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
            this.restauranteService.excluirPorId(id);
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
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "id") Long id) {

        try {
            var restaurante = this.restauranteService.consultarPorId(id);
            return ResponseEntity
                    .ok()
                    .body(restaurante);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @GetMapping(path = "/buscarTodosPorNome")
    public ResponseEntity<?> buscarTodosPorNome(@Param("nome") String nome) {

        try {
            var restaurantes = this.restauranteService.buscarTodosPorNome(nome);
            return ResponseEntity
                    .ok()
                    .body(restaurantes);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos() {

        try {
            var restaurantes = this.restauranteService.buscarTodos();
            return ResponseEntity
                    .ok()
                    .body(restaurantes);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @GetMapping(path = "/consultarPorNomeAndTaxas")
    public ResponseEntity<?> consultarPorNomeAndTaxas(@Param("nome") String nome,
                                                      @Param("freteTaxaInicial") BigDecimal freteTaxaInicial,
                                                      @Param("freteTaxaFinal") BigDecimal freteTaxaFinal) {

        try {
            var restaurantes = this.restauranteService.consultarPorNomeAndTaxas(nome, freteTaxaInicial, freteTaxaFinal);
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

