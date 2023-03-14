package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositoryJpa extends JpaRepository<Usuario, Long> { }

