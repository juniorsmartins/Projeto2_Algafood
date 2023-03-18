package io.algafoodapi.camada3_infraestrutura.repository;

import io.algafoodapi.camada2_business.model.Usuario;

import java.util.Optional;

public interface PoliticaUsuarioRepository {

    Optional<Usuario> consultarPorEmail(String email);
}

