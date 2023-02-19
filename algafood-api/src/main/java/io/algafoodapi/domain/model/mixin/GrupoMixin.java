package io.algafoodapi.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.algafoodapi.domain.model.Permissao;

import java.util.ArrayList;
import java.util.List;

public class GrupoMixin {

    @JsonIgnore
    private List<Permissao> permissoes = new ArrayList<>();
}
