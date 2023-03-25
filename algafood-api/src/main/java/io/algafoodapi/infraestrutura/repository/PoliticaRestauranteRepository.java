package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface PoliticaRestauranteRepository<I> {

    Optional<Restaurante> findById(I id);

    List<Restaurante> buscarTodosPorNome(String nome);

//    List<Restaurante> findPorCriteria(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal);
}

