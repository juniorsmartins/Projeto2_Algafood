package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cidadeService;

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade, UriComponentsBuilder uriComponentsBuilder) {

        try {
            cidade = this.cidadeService.salvar(cidade);
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .path("cidades/{id}")
                            .buildAndExpand(cidade.getId())
                            .toUri())
                    .body(cidade);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .badRequest()
                    .body(naoEncontradaException.getMessage());
        }
    }
}
