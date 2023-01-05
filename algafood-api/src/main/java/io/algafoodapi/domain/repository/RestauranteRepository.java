package io.algafoodapi.domain.repository;

import io.algafoodapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> { }

