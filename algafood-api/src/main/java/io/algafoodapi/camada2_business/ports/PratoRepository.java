package io.algafoodapi.camada2_business.ports;

import io.algafoodapi.camada2_business.model.Prato;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PratoRepository extends CustomJpaRepository<Prato, Long>, JpaSpecificationExecutor<Prato> { }
