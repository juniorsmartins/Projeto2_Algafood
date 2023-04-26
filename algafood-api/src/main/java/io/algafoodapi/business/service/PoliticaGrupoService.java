package io.algafoodapi.business.service;

import io.algafoodapi.business.model.Permissao;

import java.util.Set;

public interface PoliticaGrupoService<I> {

  Set<Permissao> consultarPermissoesPorIdDeGrupo(I id);
}

