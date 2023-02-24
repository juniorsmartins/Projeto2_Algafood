package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.api.dto.response.FormaPagamentoDtoResponse;
import io.algafoodapi.domain.service.FormaPagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/formas-pagamento")
public final class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController(final FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoDtoResponse> criar(@RequestBody @Valid final FormaPagamentoDtoRequest formaPagamentoDtoRequest,
                                                           final UriComponentsBuilder uriComponentsBuilder) {


        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/v1/formas-pagamento/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }
}

