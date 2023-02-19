package io.algafoodapi.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.algafoodapi.domain.model.Estado;

public final class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true) // Ignora o nome da cozinha a partir do restaurante (apenas na deserialização). Dá para ignorar vários nomes se colocar um array de nomes
    private Estado estado;
}
