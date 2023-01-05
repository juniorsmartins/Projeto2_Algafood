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

import java.util.Comparator;
import java.util.List;

@Service
public final class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return this.cozinhaRepository.saveAndFlush(cozinha);
    }

    public Cozinha atualizar(Long id, Cozinha cozinhaAtual) throws EntidadeNaoEncontradaException {

        var cozinha = this.consultarPorId(id);
        BeanUtils.copyProperties(cozinhaAtual, cozinha, "id");
        return this.salvar(cozinha);
    }

    public void excluir(Long id) {

        try {
            this.cozinhaRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrada cozinha com código %d.", id));

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new EntidadeEmUsoException(String.format("Não pode ser removida cozinha com código %d, pois está em uso.", id));
        }
    }

    public Cozinha consultarPorId(Long id) {

        return this.cozinhaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não encontrada cozinha com código %d.", id)));
    }

    public List<Cozinha> consultarPorNome(String nome) {

        var cozinhas = this.cozinhaRepository.findByNome(nome);

        if(cozinhas.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não encontrada cozinha com nome %s.", nome));

        return cozinhas;
    }

    public List<Cozinha> buscarTodos() {
        var cozinhas = this.cozinhaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if(cozinhas.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há cozinhas cadastradas no banco de dados."));

        return cozinhas;
    }
}
