package io.algafoodapi.camada3_infraestrutura.repository.jpa;

import io.algafoodapi.camada2_business.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositoryJpa extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String nome);

    Optional<Usuario> findByEmail(String email);
}

