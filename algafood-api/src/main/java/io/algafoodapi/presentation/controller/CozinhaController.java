package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.service.CozinhaService;
import io.algafoodapi.presentation.dto.request.CozinhaDtoRequest;
import io.algafoodapi.presentation.dto.response.CozinhaDtoResponse;
import io.algafoodapi.presentation.mapper.CozinhaMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import java.util.List;
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
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @PutMapping(path = "/{idCozinha}")
    public ResponseEntity<CozinhaDtoResponse> atualizar(@PathVariable(name = "idCozinha") final Long idCozinha,
                                                        @RequestBody @Valid final CozinhaDtoRequest cozinhaDtoRequest) {

        var response = Optional.of(cozinhaDtoRequest)
                .map(this.cozinhaMapper::converterDtoRequestParaEntidade)
                .map(kitchen -> this.cozinhaService.atualizar(idCozinha, kitchen))
                .map(this.cozinhaMapper::converterEntidadeParaDtoResponse)
                .orElseThrow();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping(path = "/{idCozinha}")
    public ResponseEntity excluirPorId(@PathVariable(name = "idCozinha") final Long cozinhaId) {

        this.cozinhaService.excluirPorId(cozinhaId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{idCozinha}")
    public ResponseEntity<CozinhaDtoResponse> consultarPorId(@PathVariable(name = "idCozinha") final Long idCozinha) {

        var response = Optional.of(this.cozinhaService.consultarPorId(idCozinha))
                .map(this.cozinhaMapper::converterEntidadeParaDtoResponse)
                .get();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping(path = "/por-nome")
    public ResponseEntity<List<CozinhaDtoResponse>> consultarPorNome(@RequestParam(name = "gastronomia") final String nome) {

        var response = this.cozinhaService.consultarPorNome(nome)
                .stream()
                .map(this.cozinhaMapper::converterEntidadeParaDtoResponse)
                .toList();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CozinhaDtoResponse>> listar(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {

      var responsePage = this.cozinhaService.listar(paginacao)
        .map(this.cozinhaMapper::converterEntidadeParaDtoResponse);

      return ResponseEntity
        .ok()
        .body(responsePage);
    }
}

