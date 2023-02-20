package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.EstadoDTORequest;
import io.algafoodapi.api.dto.response.EstadoDTOResponse;
import io.algafoodapi.domain.core.mapper.EstadoMapper;
import io.algafoodapi.domain.service.EstadoService;
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

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/estados")
public class EstadoController {

    @Autowired
    private EstadoMapper estadoMapper;

    @Autowired
    private EstadoService estadoService;

    @PostMapping
    public ResponseEntity<EstadoDTOResponse> criar(@RequestBody @Valid EstadoDTORequest estadoDTORequest, UriComponentsBuilder uriComponentsBuilder) {

        System.out.println(estadoDTORequest.nome());

        var response = Optional.of(estadoDTORequest)
                .map(this.estadoMapper::converterDTORequestParaEntidade)
                .map(state -> {
                    System.out.println(state.getNome());
                    var estadoSalvo = this.estadoService.criar(state);
                    System.out.println(estadoSalvo.getNome());
                    return estadoSalvo;
                })
                .map(this.estadoMapper::converterEntidadeParaDTOResponse)
                .orElseThrow();

        System.out.println(response.nome());

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("estados/{id}")
                        .buildAndExpand(response.id())
                        .toUri())
                .body(response);
    }

    @PutMapping(path = "/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "estadoId") Long estadoId, @RequestBody @Valid EstadoDTORequest estadoDTORequest) {

        var response = Optional.of(estadoDTORequest)
                .map(this.estadoMapper::converterDTORequestParaEntidade)
                .map(state -> this.estadoService.atualizar(estadoId, state))
                .map(this.estadoMapper::converterEntidadeParaDTOResponse)
                .orElseThrow();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping(path = "/{estadoId}")
    public ResponseEntity<?> excluirPorId(@PathVariable(name = "estadoId") Long estadoId) {

        this.estadoService.excluirPorId(estadoId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{estadoId}")
    public ResponseEntity<?> consultarPorId(@PathVariable(name = "estadoId") Long estadoId) {

        var response = this.estadoService.consultarPorId(estadoId);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> listar() {

        var estados = this.estadoService.listar();

        return ResponseEntity
                .ok()
                .body(estados);
    }
}

