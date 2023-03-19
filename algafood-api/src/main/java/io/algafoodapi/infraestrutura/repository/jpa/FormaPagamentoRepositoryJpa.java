package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagamentoRepositoryJpa extends JpaRepository<FormaPagamento, Long> { }

