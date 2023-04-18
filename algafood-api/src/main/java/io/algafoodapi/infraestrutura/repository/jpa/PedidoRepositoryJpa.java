package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepositoryJpa extends JpaRepository<Pedido, Long> { }

