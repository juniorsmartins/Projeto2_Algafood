package io.algafoodapi.camada3_infraestrutura.repository.jpa;

import io.algafoodapi.camada2_business.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepositoryJpa extends JpaRepository<Cidade, Long> { }

