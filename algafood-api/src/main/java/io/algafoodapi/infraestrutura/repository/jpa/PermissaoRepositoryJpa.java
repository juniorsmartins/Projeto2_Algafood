package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepositoryJpa extends JpaRepository<Permissao, Long> { }

