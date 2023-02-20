package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.CozinhaDtoRequest;
import io.algafoodapi.api.dto.response.CozinhaDtoResponse;
import io.algafoodapi.domain.core.mapper.CozinhaMapper;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.service.CozinhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/cozinhas")
public final class CozinhaController {

    private final CozinhaMapper cozinhaMapper;
    private final CozinhaService cozinhaService;

    public CozinhaController(final CozinhaMapper cozinhaMapper, final CozinhaService cozinhaService) {
        this.cozinhaMapper = cozinhaMapper;
        this.cozinhaService = cozinhaService;
    }
    @PostMapping
    public ResponseEntity<CozinhaDtoResponse> criar(@RequestBody @Valid final CozinhaDtoRequest cozinhaDtoRequest,
                                                    final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(cozinhaDtoRequest)
                .map(this.cozinhaMapper::converterDtoRequestParaEntidade)
                .map(this.cozinhaService::criar)
                .map(this.cozinhaMapper::converterEntidadeParaDtoResponse)
                .orElseThrow();

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("cozinhas/{id}")
                        .buildAndExpand(response.id())
                        .toUri())
                .body(response);
    }

    @PutMapping(path = "/{cozinhaId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "cozinhaId") Long cozinhaId, @RequestBody @Valid Cozinha cozinhaAtual) {

        cozinhaAtual = this.cozinhaService.atualizar(cozinhaId, cozinhaAtual);

        return ResponseEntity
                .ok()
                .body(cozinhaAtual);
    }

    @DeleteMapping(path = "/{cozinhaId}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "cozinhaId") Long cozinhaId) {

        this.cozinhaService.excluirPorId(cozinhaId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{cozinhaId}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "cozinhaId") Long cozinhaId) {

        var cozinha = this.cozinhaService.consultarPorId(cozinhaId);

        return ResponseEntity
                .ok()
                .body(cozinha);
    }

    @GetMapping(path = "/por-nome")
    public ResponseEntity<?> consultarPorNome(@RequestParam(name = "gastronomia") String nome) {

        var cozinhas = this.cozinhaService.consultarPorNome(nome);

        return ResponseEntity
                .ok()
                .body(cozinhas);
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        var cozinhas = this.cozinhaService.listar();

        return ResponseEntity
                .ok()
                .body(cozinhas);
    }
}

