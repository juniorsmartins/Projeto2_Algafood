package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepositoryJpa extends JpaRepository<Produto, Long> { }

