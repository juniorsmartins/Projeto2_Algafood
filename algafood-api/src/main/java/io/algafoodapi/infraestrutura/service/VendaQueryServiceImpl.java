package io.algafoodapi.infraestrutura.service;

import io.algafoodapi.business.model.Pedido;
import io.algafoodapi.business.service.VendaQueryService;
import io.algafoodapi.presentation.dto.response.VendaDiaria;
import io.algafoodapi.presentation.filtros.VendaDiariaFiltro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFiltro filtro) {

    var criteriaBuilder = this.entityManager.getCriteriaBuilder();
    var criteriaQuery = criteriaBuilder.createQuery(VendaDiaria.class);
    var root = criteriaQuery.from(Pedido.class);

    var funcaoDateNaDataCriacao = criteriaBuilder
      .function("date", Date.class, root.get("dataCriacao"));

    var selecao = criteriaBuilder
      .construct(VendaDiaria.class,
        funcaoDateNaDataCriacao,
        criteriaBuilder.count(root.get("id")),
        criteriaBuilder.sum(root.get("valorTotal")));

    criteriaQuery.select(selecao);
    criteriaQuery.groupBy(funcaoDateNaDataCriacao);

    return this.entityManager
      .createQuery(criteriaQuery)
      .getResultList();
  }
}

