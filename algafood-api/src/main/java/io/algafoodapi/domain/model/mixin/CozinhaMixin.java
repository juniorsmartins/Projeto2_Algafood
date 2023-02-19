package io.algafoodapi.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.algafoodapi.domain.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class CozinhaMixin {

    @JsonProperty("gastronomia")
    private String nome;
    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();
}
