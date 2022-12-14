package io.algafoodapi.api.controller;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

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

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long id, @RequestBody Cidade cidade) {

        try {
            cidade = this.cidadeService.atualizar(id, cidade);
            return ResponseEntity
                    .ok()
                    .body(cidade);

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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable(name = "id") Long id) {

        try {
            this.cidadeService.excluirPorId(id);
            return ResponseEntity
                    .noContent()
                    .build();

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());

        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscar(@PathVariable(name = "id") Long id) {

        try {
            var cidade = this.cidadeService.consultarPorId(id);
            return ResponseEntity
                    .ok()
                    .body(cidade);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(naoEncontradaException.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        try {
            var cidades = this.cidadeService.buscarTodos();
            return ResponseEntity
                    .ok()
                    .body(cidades);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
}
