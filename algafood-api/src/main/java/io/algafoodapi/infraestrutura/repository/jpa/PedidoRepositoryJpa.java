package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PedidoRepositoryJpa extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

//  List<VendaDiaria> consultarVendasDiarias(VendaDiariaFiltro filtro);
}

