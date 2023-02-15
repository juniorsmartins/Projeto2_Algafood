package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid Restaurante restaurante,
                                   UriComponentsBuilder uriComponentsBuilder) {

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
                       @RequestBody @Valid Restaurante restauranteAtual) {

        restauranteAtual = this.restauranteService.atualizar(restauranteId, restauranteAtual);

        return ResponseEntity
                .ok()
                .body(restauranteAtual);
    }

    @PatchMapping(path = "/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable(name = "restauranteId") Long restauranteId,
                                              @RequestBody Map<String, Object> campos,
                                              HttpServletRequest request) {

            var restaurante = this.restauranteService.atualizarParcial(
                    restauranteId, campos, request);

            return ResponseEntity
                    .ok()
                    .body(restaurante);
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

