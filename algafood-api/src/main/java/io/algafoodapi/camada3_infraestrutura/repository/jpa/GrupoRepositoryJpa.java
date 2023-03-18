package io.algafoodapi.camada3_infraestrutura.repository.jpa;

import io.algafoodapi.camada2_business.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepositoryJpa extends JpaRepository<Grupo, Long> { }

