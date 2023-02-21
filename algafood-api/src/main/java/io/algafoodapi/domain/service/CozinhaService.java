package io.algafoodapi.domain.service;

import io.algafoodapi.domain.core.Constantes;
import io.algafoodapi.domain.exception.http404.CozinhaNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.CozinhaEmUsoException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public CozinhaService(final CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cozinha criar(final Cozinha cozinha) {
        return this.cozinhaRepository.save(cozinha);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cozinha atualizar(final Long idCozinha, final Cozinha cozinha) {

        return this.cozinhaRepository.findById(idCozinha)
                .map(kitchen -> {
                    BeanUtils.copyProperties(cozinha, kitchen, "id");
                    return kitchen;
                })
                .orElseThrow(() -> new CozinhaNaoEncontradaException(idCozinha));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(final Long id) {

        try {
            this.cozinhaRepository.deleteById(id);
            this.cozinhaRepository.flush(); // Manda descarregar as operações antes do commit da transação

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new CozinhaNaoEncontradaException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CozinhaEmUsoException(id); 
        }
    }

    @Transactional(readOnly = true)
    public Cozinha consultarPorId(final Long idCozinha) {

        return this.cozinhaRepository.findById(idCozinha)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(idCozinha));
    }

    @Transactional(readOnly = true)
    public List<Cozinha> consultarPorNome(final String nome) {

        var cozinhas = this.cozinhaRepository.findTodasByNomeContaining(nome)
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if (cozinhas.isEmpty()) {
            throw new CozinhaNaoEncontradaException(String.format(Constantes.NÃO_ENCONTRADA_COZINHA_COM_NOME, nome));
        }

        return cozinhas;
    }

    @Transactional(readOnly = true)
    public List<Cozinha> listar() {

        var cozinhas = this.cozinhaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if (cozinhas.isEmpty()) {
            throw new CozinhaNaoEncontradaException(Constantes.NAO_EXISTEM_COZINHAS_CADASTRADAS);
        }

        return cozinhas;
    }
}

