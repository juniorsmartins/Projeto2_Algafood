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
public class CozinhaService {

    public static final String NAO_ENCONTRADA_COZINHA_COM_ID = "Não encontrada cozinha com código %d.";
    public static final String NÃO_ENCONTRADA_COZINHA_COM_NOME = "Não encontrada cozinha com nome %s.";
    public static final String NAO_EXISTEM_COZINHAS_CADASTRADAS = "Não existem cozinhas cadastradas.";
    public static final String PROIBIDO_APAGAR_COZINHA_EM_USO_COM_ID = "Proibido apagar cozinha em uso. ID: %d.";

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
            throw new EntidadeNaoEncontradaException(String.format(NAO_ENCONTRADA_COZINHA_COM_ID, id));

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new EntidadeEmUsoException(String.format(PROIBIDO_APAGAR_COZINHA_EM_USO_COM_ID, id));
        }
    }

    public Cozinha consultarPorId(Long id) {

        return this.cozinhaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(NAO_ENCONTRADA_COZINHA_COM_ID, id)));
    }

    public List<Cozinha> consultarPorNome(String nome) {

        var cozinhas = this.cozinhaRepository.findTodasByNomeContaining(nome);

        if (cozinhas.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format(NÃO_ENCONTRADA_COZINHA_COM_NOME, nome));

        return cozinhas;
    }

    public List<Cozinha> listar() {

        var cozinhas = this.cozinhaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cozinha::getId).reversed())
                .toList();

        if(cozinhas.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format(NAO_EXISTEM_COZINHAS_CADASTRADAS));

        return cozinhas;
    }
}

