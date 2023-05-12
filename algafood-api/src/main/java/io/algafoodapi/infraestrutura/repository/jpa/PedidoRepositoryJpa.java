package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.presentation.dto.response.VendaDiaria;
import io.algafoodapi.presentation.filtros.VendaDiariaFiltro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PedidoRepositoryJpa extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

  List<VendaDiaria> consultarVendasDiarias(VendaDiariaFiltro filtro);
}

