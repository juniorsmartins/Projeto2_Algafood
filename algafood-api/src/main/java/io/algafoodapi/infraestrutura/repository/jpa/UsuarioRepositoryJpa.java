package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositoryJpa extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String nome);

    Optional<Usuario> findByEmail(String email);
}

