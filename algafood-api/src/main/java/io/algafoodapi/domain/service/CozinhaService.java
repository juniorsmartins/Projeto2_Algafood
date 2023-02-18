package io.algafoodapi.domain.service;

import io.algafoodapi.domain.core.Constantes;
import io.algafoodapi.domain.exception.http404.CozinhaNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.CozinhaEmUsoException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cozinha criar(Cozinha cozinha) {
        return this.cozinhaRepository.saveAndFlush(cozinha);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cozinha atualizar(Long id, Cozinha cozinhaAtual) {

        var cozinha = this.consultarPorId(id);
        BeanUtils.copyProperties(cozinhaAtual, cozinha, "id");

        return this.criar(cozinha);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(Long id) {

        try {
            this.cozinhaRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new CozinhaNaoEncontradaException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CozinhaEmUsoException(id);
        }
    }

    @Transactional(readOnly = true)
    public Cozinha consultarPorId(Long id) {

        return this.cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    @Transactional(readOnly = true)
    public List<Cozinha> consultarPorNome(String nome) {

        var cozinhas = this.cozinhaRepository.findTodasByNomeContaining(nome)
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if (cozinhas.isEmpty())
            throw new CozinhaNaoEncontradaException(String.format(Constantes.NÃO_ENCONTRADA_COZINHA_COM_NOME, nome));

        return cozinhas;
    }

    @Transactional(readOnly = true)
    public List<Cozinha> listar() {

        var cozinhas = this.cozinhaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if(cozinhas.isEmpty())
            throw new CozinhaNaoEncontradaException(Constantes.NAO_EXISTEM_COZINHAS_CADASTRADAS);

        return cozinhas;
    }
}

