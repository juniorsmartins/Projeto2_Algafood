package io.algafoodapi.camada2_business.service;

import io.algafoodapi.camada1_presentation.dto.request.PoliticaTrocarSenhaDtoRequest;

public interface PoliticaUsuarioService<T extends PoliticaTrocarSenhaDtoRequest<I, S>, I, S> {

  S trocarSenha(T dtoRequest);
}

