package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.PoliticaTrocarSenhaDtoRequest;
import io.algafoodapi.presentation.dto.response.GrupoDtoResponse;
import io.algafoodapi.presentation.dto.response.UsuarioDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Set;

public interface PoliticaUsuarioController<T extends PoliticaTrocarSenhaDtoRequest<I, S>, I, S> {

  @PatchMapping
  ResponseEntity<S> trocarSenha(T dtoRequest);

  @GetMapping(path = "/{idUsuario}/grupos")
  ResponseEntity<Set<GrupoDtoResponse>> consultarGruposPorIdDeUsuario(I idUsuario);

  @PutMapping(path = "/{idUsuario}/grupos/{idGrupo}")
  ResponseEntity<UsuarioDtoResponse> associarNoUsuarioPorIdUmGrupoPorId(I idUsuario, I idGrupo);

  @DeleteMapping(path = "/{idUsuario}/grupos/{idGrupo}")
  ResponseEntity<?> removerDoUsuarioPorIdUmGrupoPorId(I idUsuario, I idGrupo);
}

