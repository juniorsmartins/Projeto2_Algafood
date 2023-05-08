package io.algafoodapi.presentation.controller;

import io.algafoodapi.business.service.CidadeService;
import io.algafoodapi.presentation.dto.request.CidadeDtoRequest;
import io.algafoodapi.presentation.dto.response.CidadeDtoResponse;
import io.algafoodapi.presentation.mapper.CidadeMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/cidades")
public final class CidadeController {

    @Autowired
    private CidadeMapper cidadeMapper;

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<CidadeDtoResponse> cadastrar(@RequestBody @Valid final CidadeDtoRequest cidadeDtoRequest,
                                                       final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(cidadeDtoRequest)
            .map(this.cidadeMapper::converterDtoRequestParaEntidade)
            .map(this.cidadeService::cadastrar)
            .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .created(uriComponentsBuilder
                .path("/v1/cidades/{codigoCidade}")
                .buildAndExpand(response.getCodigo())
                .toUri())
            .body(response);
    }

    @PutMapping(path = "/{codigoCidade}")
    public ResponseEntity<CidadeDtoResponse> atualizar(@PathVariable(name = "codigoCidade") final String codigoCidade,
                                       @RequestBody @Valid final CidadeDtoRequest cidadeDtoRequest) {

        var response = Optional.of(cidadeDtoRequest)
            .map(this.cidadeMapper::converterDtoRequestParaEntidade)
            .map(city -> this.cidadeService.atualizar(codigoCidade, city))
            .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @DeleteMapping(path = "/{codigoCidade}")
    public ResponseEntity excluirPorId(@PathVariable(name = "codigoCidade") final String codigoCidade) {

        this.cidadeService.excluirPorCodigo(codigoCidade);

        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping(path = "/{codigoCidade}")
    public ResponseEntity<CidadeDtoResponse> consultarPorId(@PathVariable(name = "codigoCidade") final String codigoCidade) {

        var response = Optional.of(this.cidadeService.consultarPorCodigo(codigoCidade))
            .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
            .get();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CidadeDtoResponse>> listar() {

        var cidades = this.cidadeService.listar()
            .stream()
            .map(this.cidadeMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(cidades);
    }
}

