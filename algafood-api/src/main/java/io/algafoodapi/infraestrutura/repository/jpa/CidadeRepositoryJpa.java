package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepositoryJpa extends JpaRepository<Cidade, Long> {

  // ---------- Derived Queries ---------- //
  Optional<Cidade> findByCodigo(String codigo);

  void deleteByCodigo(String codigo);
}

