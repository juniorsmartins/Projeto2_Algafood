package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.PoliticaDtoRequest;
import io.algafoodapi.presentation.dto.response.PermissaoDtoResponse;
import io.algafoodapi.presentation.dto.response.PoliticaDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Set;

public interface PoliticaGrupoController<R extends PoliticaDtoRequest, S extends PoliticaDtoResponse<I>, I> {

  @GetMapping(path = "/{id}/permissoes")
  ResponseEntity<Set<PermissaoDtoResponse>> consultarPermissoesPorIdDeGrupo(I id);

  @PutMapping(path = "/{idGrupo}/permissoes/{idPermissao}")
  ResponseEntity<S> associarPermissaoNoGrupoPorIds(I idGrupo, I idPermissao);
}

