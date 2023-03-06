package io.algafoodapi.domain.ports;

import io.algafoodapi.domain.model.Grupo;

import java.util.List;

public interface GrupoRepository {

    Grupo salvar(Grupo grupo);

    List<Grupo> listar();
}

