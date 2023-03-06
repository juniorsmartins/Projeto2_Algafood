package io.algafoodapi.domain.service;

import io.algafoodapi.domain.core.Constantes;
import io.algafoodapi.domain.exception.http409.NomeDeGrupoEmUsoException;
import io.algafoodapi.domain.model.Grupo;
import io.algafoodapi.domain.ports.GrupoRepository;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GrupoServiceImpl implements GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Grupo cadastrar(Grupo grupo) {

        return Optional.of(grupo)
            .map(this::regraGarantirNomeÚnico)
            .map(this::regraCapitalizarNome)
            .map(this.grupoRepository::salvar)
            .orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Grupo> pesquisar(final Grupo grupo, final Pageable paginacao) {

        var condicoesDePesquisa = this.criarCondicoesParaPesquisar(grupo);

        return this.grupoRepository.pesquisar(condicoesDePesquisa, paginacao);
    }

    private Grupo regraCapitalizarNome(Grupo grupo) {
        var nomeParaCapitalizar = grupo.getNome();
        var nomeCapitalizado = WordUtils.capitalizeFully(nomeParaCapitalizar);
        grupo.setNome(nomeCapitalizado);
        return grupo;
    }

    private Grupo regraGarantirNomeÚnico(Grupo grupo) {
        var nomeDeEntradaParaPadronizar = grupo.getNome();
        var nomeDeEntradaPadronizado = this.padronizarNome(nomeDeEntradaParaPadronizar);

        var existeNomeIgual = this.grupoRepository.listar()
            .stream()
            .map(Grupo::getNome)
            .map(this::padronizarNome)
            .anyMatch(nomeDoDatabasePadronizado -> nomeDoDatabasePadronizado.equals(nomeDeEntradaPadronizado));

        if (existeNomeIgual) {
            throw new NomeDeGrupoEmUsoException(String.format(Constantes.NOME_DE_GRUPO_JA_CADASTRADO, nomeDeEntradaParaPadronizar));
        }

        return grupo;
    }

    private String padronizarNome(String nome) {
        return Optional.of(nome)
            .map(nomeParaPadronizar -> nomeParaPadronizar.toLowerCase().trim())
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ç", "c"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "á", "a"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "â", "a"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "à", "a"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ã", "a"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "é", "e"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ê", "e"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "è", "e"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "í", "i"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "î", "i"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ì", "i"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ó", "o"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ô", "o"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ò", "o"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "õ", "o"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ú", "u"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "û", "u"))
            .map(nomeParaPadronizar -> RegExUtils.replaceAll(nomeParaPadronizar, "ù", "u"))
            .orElseThrow();
    }

    private Example criarCondicoesParaPesquisar(Grupo grupo) {
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withIgnoreNullValues()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(grupo, matcher);
    }
}

