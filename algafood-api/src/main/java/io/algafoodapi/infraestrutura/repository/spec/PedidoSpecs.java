package io.algafoodapi.infraestrutura.repository.spec;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.presentation.filtros.PedidoFiltro;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class PedidoSpecs {

  public static Specification<Pedido> pesquisaDinamicaComFiltro(PedidoFiltro pedidoFiltro) {

    return ((root, query, criteriaBuilder) -> {

      if (Pedido.class.equals(query.getResultType())) {
        root.fetch("restaurante").fetch("cozinha");
        root.fetch("usuario");
      }

      var predicados = new ArrayList<Predicate>();

      if (ObjectUtils.isNotEmpty(pedidoFiltro.getId())) {
        predicados.add(criteriaBuilder.equal(root.get("id"), pedidoFiltro.getId()));
      }

      if (ObjectUtils.isNotEmpty(pedidoFiltro.getStatusPedido())) {
        predicados.add(criteriaBuilder.equal(root.get("statusPedido"), pedidoFiltro.getStatusPedido()));
      }

      if (ObjectUtils.isNotEmpty(pedidoFiltro.getFormaPagamento())) {
        predicados.add(criteriaBuilder.equal(root.get("formaPagamento"), pedidoFiltro.getFormaPagamento()));
      }

      if (ObjectUtils.isNotEmpty(pedidoFiltro.getRestaurante())) {
        predicados.add(criteriaBuilder.equal(root.get("restaurante"), pedidoFiltro.getRestaurante()));
      }

      if (ObjectUtils.isNotEmpty(pedidoFiltro.getUsuario())) {
        predicados.add(criteriaBuilder.equal(root.get("usuario"), pedidoFiltro.getUsuario()));
      }

      return criteriaBuilder.and(predicados.toArray(new Predicate[0]));
    });
  }
}

