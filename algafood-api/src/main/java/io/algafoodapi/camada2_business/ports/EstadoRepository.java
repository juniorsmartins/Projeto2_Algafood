package io.algafoodapi.camada2_business.ports;

import io.algafoodapi.camada2_business.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> { }

