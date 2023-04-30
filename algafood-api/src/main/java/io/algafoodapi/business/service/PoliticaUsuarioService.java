package io.algafoodapi.business.service;

import io.algafoodapi.business.model.Grupo;
import io.algafoodapi.business.model.Usuario;
import io.algafoodapi.presentation.dto.request.PoliticaTrocarSenhaDtoRequest;

import java.util.Set;

public interface PoliticaUsuarioService<T extends PoliticaTrocarSenhaDtoRequest<I, S>, I, S> {

  S trocarSenha(T dtoRequest);

  Set<Grupo> consultarGruposPorIdDeUsuario(I idUsuario);

  Usuario associarNoUsuarioPorIdUmGrupoPorId(I idUsuario, I idGrupo);

  void removerDoUsuarioPorIdUmGrupoPorId(I idUsuario, I idGrupo);
}

