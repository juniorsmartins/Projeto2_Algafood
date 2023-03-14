package io.algafoodapi.domain.utils;

import io.algafoodapi.domain.exception.http409.NomeDeUsuarioEmUsoException;
import io.algafoodapi.domain.model.PoliticaEntidade;
import io.algafoodapi.infraestrutura.repository.PoliticaRepository;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUtils {

    public <E extends PoliticaEntidade, R extends PoliticaRepository<?, Long>> E regraGarantirNomeUnico(E entidade, R repository) {
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

    public String padronizarNome(String nome) {
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

    public <E extends PoliticaEntidade> E capitalizarNome(E entidade) {
        var nomeParaCapitalizar = entidade.getNome().trim();
        var nomeCapitalizado = WordUtils.capitalizeFully(nomeParaCapitalizar);

        var nomeCapitalizadoComDaDeDiDoDuMinusculo = Optional.of(nomeCapitalizado)
                .map(nome -> RegExUtils.replaceAll(nome, " Da ", " da "))
                .map(nome -> RegExUtils.replaceAll(nome, " De ", " de "))
                .map(nome -> RegExUtils.replaceAll(nome, " Di ", " di "))
                .map(nome -> RegExUtils.replaceAll(nome, " Do ", " do "))
                .map(nome -> RegExUtils.replaceAll(nome, " Du ", " du "))
                .orElseThrow();

        entidade.setNome(nomeCapitalizadoComDaDeDiDoDuMinusculo);
        return entidade;
    }
}

