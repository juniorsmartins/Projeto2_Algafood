package io.algafoodapi.api.controller;

import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.model.CozinhasXmlWrapper;
import io.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }) // Content Negotiation (Json e XML)
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionar(@RequestBody Cozinha cozinha) {
        this.cozinhaRepository.salvar(cozinha);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable(name = "id") Long id, @RequestBody Cozinha cozinhaAtual) {

        var cozinha = this.cozinhaRepository.buscar(id);

        if(cozinha == null)
            return ResponseEntity
                    .notFound()
                    .build();

        BeanUtils.copyProperties(cozinhaAtual, cozinha, "id");
        this.cozinhaRepository.salvar(cozinha);

        return ResponseEntity
                .ok(cozinha);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable(name = "id") Long id) {

        var cozinha = this.cozinhaRepository.buscar(id);

        if(cozinha == null)
            return ResponseEntity
                    .notFound()
                    .build();

        this.cozinhaRepository.remover(cozinha);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar() {
        return this.cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(this.cozinhaRepository.listar());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable(name = "id") Long id) {

        var cozinha = this.cozinhaRepository.buscar(id);

        if(cozinha == null)
            return ResponseEntity
                    .notFound()
                    .build();

        return ResponseEntity
                .ok(cozinha);
    }
}
