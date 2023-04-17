package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.presentation.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.presentation.mapper.FormaPagamentoMapper;
import io.algafoodapi.business.service.FormaPagamentoService;
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

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/formas-pagamento")
public final class FormaPagamentoController {

    @Autowired
    private FormaPagamentoMapper formaPagamentoMapper;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @PostMapping
    public ResponseEntity<FormaPagamentoDtoResponse> cadastrar(@RequestBody @Valid final FormaPagamentoDtoRequest dtoRequest,
                                                               final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(dtoRequest)
            .map(this.formaPagamentoMapper::converterDtoRequestParaEntidade)
            .map(this.formaPagamentoService::cadastrar)
            .map(this.formaPagamentoMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .created(uriComponentsBuilder
                .path("/v1/formas-pagamento/{id}")
                .buildAndExpand(response.getId())
                .toUri())
            .body(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<FormaPagamentoDtoResponse> atualizar(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FormaPagamentoDtoRequest atualizarDtoRequest) {

        var response = Optional.of(atualizarDtoRequest)
            .map(this.formaPagamentoMapper::converterDtoRequestParaEntidade)
            .map(formaPagamento -> this.formaPagamentoService.atualizar(id, formaPagamento))
            .map(this.formaPagamentoMapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDtoResponse>> listar() {
        var response = this.formaPagamentoService.listar()
            .stream()
            .map(this.formaPagamentoMapper::converterEntidadeParaDtoResponse)
            .toList();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletarPorId(@PathVariable(name = "id") final Long id) {
        this.formaPagamentoService.deletarPorId(id);

        return ResponseEntity
            .noContent()
            .build();
    }
}

