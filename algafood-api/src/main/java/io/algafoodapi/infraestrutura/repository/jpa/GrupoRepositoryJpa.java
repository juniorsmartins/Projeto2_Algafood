package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.domain.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepositoryJpa extends JpaRepository<Grupo, Long> { }

