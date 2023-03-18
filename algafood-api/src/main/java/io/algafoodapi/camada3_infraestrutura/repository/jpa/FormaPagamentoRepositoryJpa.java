package io.algafoodapi.camada3_infraestrutura.repository.jpa;

import io.algafoodapi.camada2_business.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagamentoRepositoryJpa extends JpaRepository<FormaPagamento, Long> { }

