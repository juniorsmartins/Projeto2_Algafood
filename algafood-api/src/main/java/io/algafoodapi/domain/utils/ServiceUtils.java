package io.algafoodapi.domain.utils;

import org.apache.commons.lang3.RegExUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class ServiceUtils {

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
}

