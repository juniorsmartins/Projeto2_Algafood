package io.algafoodapi.business.ports;

import io.algafoodapi.business.model.Prato;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PratoRepository extends CustomJpaRepository<Prato, Long>, JpaSpecificationExecutor<Prato> { }
