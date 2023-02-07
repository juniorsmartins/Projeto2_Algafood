package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.http404.CozinhaNaoEncontradaException;
import io.algafoodapi.domain.exception.http404.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http409.CozinhaEmUsoException;
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
public class CozinhaService {

    public static final String NÃO_ENCONTRADA_COZINHA_COM_NOME = "Não encontrada cozinha com nome %s.";
    public static final String NAO_EXISTEM_COZINHAS_CADASTRADAS = "Não existem cozinhas cadastradas.";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha criar(Cozinha cozinha) {
        return this.cozinhaRepository.saveAndFlush(cozinha);
    }

    public Cozinha atualizar(Long id, Cozinha cozinhaAtual) {

        var cozinha = this.consultarPorId(id);
        BeanUtils.copyProperties(cozinhaAtual, cozinha, "id");

        return this.criar(cozinha);
    }

    public void excluirPorId(Long id) {

        try {
            this.cozinhaRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new CozinhaNaoEncontradaException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CozinhaEmUsoException(id);
        }
    }

    public Cozinha consultarPorId(Long id) {

        return this.cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    public List<Cozinha> consultarPorNome(String nome) {

        var cozinhas = this.cozinhaRepository.findTodasByNomeContaining(nome)
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if (cozinhas.isEmpty())
            throw new CozinhaNaoEncontradaException(String.format(NÃO_ENCONTRADA_COZINHA_COM_NOME, nome));

        return cozinhas;
    }

    public List<Cozinha> listar() {

        var cozinhas = this.cozinhaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if(cozinhas.isEmpty())
            throw new CozinhaNaoEncontradaException(String.format(NAO_EXISTEM_COZINHAS_CADASTRADAS));

        return cozinhas;
    }
}

