package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.api.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.api.mapper.FormaPagamentoMapper;
import io.algafoodapi.domain.service.FormaPagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/formas-pagamento")
public final class FormaPagamentoController {

    private final FormaPagamentoMapper formaPagamentoMapper;
    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController(final FormaPagamentoMapper formaPagamentoMapper,
                                    final FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoMapper = formaPagamentoMapper;
        this.formaPagamentoService = formaPagamentoService;
    }

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

//    @GetMapping
//    public ResponseEntity<List<FormaPagamentoDtoResponse>> listar() {
//
//    }
}

