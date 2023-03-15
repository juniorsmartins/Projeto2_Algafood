package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.domain.model.Usuario;

import java.util.Optional;

public interface PoliticaUsuarioRepository {

    Optional<Usuario> consultarPorEmail(String email);
}

