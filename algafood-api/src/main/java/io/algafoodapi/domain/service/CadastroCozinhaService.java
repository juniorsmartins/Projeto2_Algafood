package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return this.cozinhaRepository.salvar(cozinha);
    }

    public Cozinha atualizar(Long id, Cozinha cozinhaAtual) throws EntidadeNaoEncontradaException {

        var cozinha = this.buscar(id);
        BeanUtils.copyProperties(cozinhaAtual, cozinha, "id");
        return this.salvar(cozinha);
    }

    public void excluir(Long id) {

        try {
            this.cozinhaRepository.remover(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrada cozinha com código %d.", id));

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new EntidadeEmUsoException(String.format("Não pode ser removida cozinha com código %d, pois está em uso.", id));
        }
    }

    public Cozinha buscar(Long id) {

        var cozinha = this.cozinhaRepository.buscar(id);

        if(cozinha == null)
            throw new EntidadeNaoEncontradaException("""
                    Não encontrada cozinha com código %d.""".formatted(id));

        return cozinha;
    }

    public List<Cozinha> listar() {
        var cozinhas = this.cozinhaRepository.listar();

        if(cozinhas.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há cozinhas cadastradas no banco de dados."));

        return cozinhas;
    }
}
