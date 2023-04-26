package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.response.PermissaoDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

public interface PoliticaGrupoController {

  @GetMapping(path = "/{id}/permissoes")
  ResponseEntity<Set<PermissaoDtoResponse>> consultarPermissoesPorIdDeGrupo(Long id);
}

