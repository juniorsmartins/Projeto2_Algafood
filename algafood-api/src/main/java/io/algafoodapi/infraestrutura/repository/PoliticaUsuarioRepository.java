package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Usuario;

import java.util.Optional;

public interface PoliticaUsuarioRepository {

    Optional<Usuario> consultarPorEmail(String email);
}

