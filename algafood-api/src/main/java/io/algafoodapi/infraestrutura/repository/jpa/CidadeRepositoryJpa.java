package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepositoryJpa extends JpaRepository<Cidade, Long> { }

