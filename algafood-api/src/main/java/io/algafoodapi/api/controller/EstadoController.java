package io.algafoodapi.api.controller;

import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.EstadoRepository;
import io.algafoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private CadastroEstadoService estadoService;

    @Autowired
    private EstadoRepository estadoRepository;

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado, UriComponentsBuilder uriComponentsBuilder) {

        estado = this.estadoService.salvar(estado);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("estados/{id}")
                        .buildAndExpand(estado.getId())
                        .toUri())
                .body(estado);
    }

    @GetMapping
    public List<Estado> listar() {
        return this.estadoRepository.listar();
    }
}
