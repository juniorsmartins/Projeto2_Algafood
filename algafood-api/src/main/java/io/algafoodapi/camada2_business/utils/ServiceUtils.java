package io.algafoodapi.camada2_business.utils;

import io.algafoodapi.camada2_business.exception.http409.NomeDeUsuarioEmUsoException;
import io.algafoodapi.camada2_business.model.PoliticaEntidade;
import io.algafoodapi.camada3_infraestrutura.repository.PoliticaCrudBaseRepository;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUtils {

    public <E extends PoliticaEntidade, R extends PoliticaCrudBaseRepository<?, Long>> E regraGarantirNomeUnico(E entidade, R repository) {
        var nomeDeEntradaParaPadronizar = entidade.getNome().trim();
        var nomeDeEntradaPadronizado = this.padronizarNome(nomeDeEntradaParaPadronizar);

        var existeNomeIgual = repository.listar()
            .stream()
            .filter(entidadeDoDatabase -> entidadeDoDatabase.getId() != entidade.getId())
            .map(entidadeDoDatabase -> entidadeDoDatabase.getNome())
            .map(this::padronizarNome)
            .anyMatch(nomeDoDatabasePadronizado -> nomeDoDatabasePadronizado.equals(nomeDeEntradaPadronizado));

        if (existeNomeIgual) {
            throw new NomeDeUsuarioEmUsoException(nomeDeEntradaPadronizado);
        }

        return entidade;
    }

    public String padronizarNome(String nomeParaPadronizar) {

        return Optional.of(nomeParaPadronizar)
            .map(nome -> nome.toLowerCase().trim())
            .map(nome -> RegExUtils.replaceAll(nome, "ç", "c"))
            .map(nome -> RegExUtils.replaceAll(nome, "á", "a"))
            .map(nome -> RegExUtils.replaceAll(nome, "â", "a"))
            .map(nome -> RegExUtils.replaceAll(nome, "à", "a"))
            .map(nome -> RegExUtils.replaceAll(nome, "ã", "a"))
            .map(nome -> RegExUtils.replaceAll(nome, "é", "e"))
            .map(nome -> RegExUtils.replaceAll(nome, "ê", "e"))
            .map(nome -> RegExUtils.replaceAll(nome, "è", "e"))
            .map(nome -> RegExUtils.replaceAll(nome, "í", "i"))
            .map(nome -> RegExUtils.replaceAll(nome, "î", "i"))
            .map(nome -> RegExUtils.replaceAll(nome, "ì", "i"))
            .map(nome -> RegExUtils.replaceAll(nome, "ó", "o"))
            .map(nome -> RegExUtils.replaceAll(nome, "ô", "o"))
            .map(nome -> RegExUtils.replaceAll(nome, "ò", "o"))
            .map(nome -> RegExUtils.replaceAll(nome, "õ", "o"))
            .map(nome -> RegExUtils.replaceAll(nome, "ú", "u"))
            .map(nome -> RegExUtils.replaceAll(nome, "û", "u"))
            .map(nome -> RegExUtils.replaceAll(nome, "ù", "u"))
            .orElseThrow();
    }

    public <E extends PoliticaEntidade> E capitalizarNome(E entidade) {
        var nomeParaCapitalizar = entidade.getNome().trim();
        var nomeCapitalizado = WordUtils.capitalizeFully(nomeParaCapitalizar);

        var nomeCapitalizadoComDaDeDiDoDuEmNaMinusculo = Optional.of(nomeCapitalizado)
            .map(nome -> RegExUtils.replaceAll(nome, " Da ", " da "))
            .map(nome -> RegExUtils.replaceAll(nome, " De ", " de "))
            .map(nome -> RegExUtils.replaceAll(nome, " Di ", " di "))
            .map(nome -> RegExUtils.replaceAll(nome, " Do ", " do "))
            .map(nome -> RegExUtils.replaceAll(nome, " Du ", " du "))
            .map(nome -> RegExUtils.replaceAll(nome, " Em ", " em "))
            .map(nome -> RegExUtils.replaceAll(nome, " Um ", " um "))
            .map(nome -> RegExUtils.replaceAll(nome, " Na ", " na "))
            .map(nome -> RegExUtils.replaceAll(nome, " Ne ", " ne "))
            .map(nome -> RegExUtils.replaceAll(nome, " Ni ", " ni "))
            .map(nome -> RegExUtils.replaceAll(nome, " No ", " no "))
            .map(nome -> RegExUtils.replaceAll(nome, " Nu ", " nu "))
            .orElseThrow();

        entidade.setNome(nomeCapitalizadoComDaDeDiDoDuEmNaMinusculo);
        return entidade;
    }

    public <E extends PoliticaEntidade> Example criarCondicoesDePesquisa(final E entidade) {

        ExampleMatcher exampleMatcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withIgnoreNullValues()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(entidade, exampleMatcher);
    }
}

