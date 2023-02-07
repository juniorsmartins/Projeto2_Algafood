package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
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
@RequestMapping(path = "/v1/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Restaurante restaurante, UriComponentsBuilder uriComponentsBuilder) {

        restaurante = this.restauranteService.criar(restaurante);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("restaurantes/{id}")
                        .buildAndExpand(restaurante.getId())
                        .toUri())
                .body(restaurante);
    }

    @PutMapping(path = "/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "restauranteId") Long restauranteId,
                                       @RequestBody Restaurante restauranteAtual) {

        restauranteAtual = this.restauranteService.atualizar(restauranteId, restauranteAtual);

        return ResponseEntity
                .ok()
                .body(restauranteAtual);
    }

    @PatchMapping(path = "/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable(name = "restauranteId") Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {

        try {
            var restaurante = this.restauranteService.atualizarParcial(restauranteId, campos);
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

    @DeleteMapping(path = "/{restauranteId}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "restauranteId") Long restauranteId) {

        this.restauranteService.excluirPorId(restauranteId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{restauranteId}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "restauranteId") Long restauranteId) {

        var restaurante = this.restauranteService.consultarPorId(restauranteId);

        return ResponseEntity
                .ok()
                .body(restaurante);
    }

    @GetMapping(path = "/por-nome")
    public ResponseEntity<?> consultarPorNome(@RequestParam(name = "nome") String nome) {

        var restaurantes = this.restauranteService.consultarPorNome(nome);

        return ResponseEntity
                .ok()
                .body(restaurantes);
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        var restaurantes = this.restauranteService.listar();

        return ResponseEntity
                .ok()
                .body(restaurantes);
    }

    @GetMapping(path = "/por-nome-e-taxas")
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

