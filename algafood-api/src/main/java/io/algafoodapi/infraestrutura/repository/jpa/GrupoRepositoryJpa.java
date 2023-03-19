package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepositoryJpa extends JpaRepository<Grupo, Long> { }

