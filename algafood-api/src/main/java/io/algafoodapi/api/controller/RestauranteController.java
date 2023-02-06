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
@RequestMapping(path = "/restaurantes")
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

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long id, @RequestBody Restaurante restauranteAtual) {

        restauranteAtual = this.restauranteService.atualizar(id, restauranteAtual);

        return ResponseEntity
                .ok()
                .body(restauranteAtual);
    }

    @PatchMapping(path = "/{id}")
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "id") Long id) {

        this.restauranteService.excluirPorId(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "id") Long id) {

        var restaurante = this.restauranteService.consultarPorId(id);

        return ResponseEntity
                .ok()
                .body(restaurante);
    }

    @GetMapping(path = "/porNome")
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

