package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public final class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository repository;

    public Cozinha salvar(Cozinha cozinha) {
        return this.repository.salvar(cozinha);
    }

    public void excluir(Long id) {
        try {
            this.repository.remover(id);

        } catch (EmptyResultDataAccessException data) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrada cozinha com código %d.", id));

        } catch (DataIntegrityViolationException data) {
            throw new EntidadeEmUsoException(String.format("Não pode ser removida cozinha com código %d, pois está em uso.", id));
        }
    }
}
