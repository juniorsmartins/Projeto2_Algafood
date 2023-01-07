package io.algafoodapi.domain.repository;

import io.algafoodapi.domain.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PratoRepository extends CustomJpaRepository<Prato, Long>, JpaSpecificationExecutor<Prato> { }
