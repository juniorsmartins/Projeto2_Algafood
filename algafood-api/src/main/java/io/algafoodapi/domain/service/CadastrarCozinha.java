package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CadastrarCozinha {

    private CozinhaRepository repository;

    public List<Cozinha> listar() {
        return repository.listar();
    }
}
