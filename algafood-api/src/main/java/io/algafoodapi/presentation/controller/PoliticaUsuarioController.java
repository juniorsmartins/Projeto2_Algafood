package io.algafoodapi.presentation.controller;

import io.algafoodapi.presentation.dto.request.PoliticaTrocarSenhaDtoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;

public interface PoliticaUsuarioController<T extends PoliticaTrocarSenhaDtoRequest<I, S>, I, S> {

  @PatchMapping
  ResponseEntity<S> trocarSenha(T dtoRequest);
}

