package io.algafoodapi.business.service;

import io.algafoodapi.presentation.dto.request.PoliticaTrocarSenhaDtoRequest;

public interface PoliticaUsuarioService<T extends PoliticaTrocarSenhaDtoRequest<I, S>, I, S> {

  S trocarSenha(T dtoRequest);
}

