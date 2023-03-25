package io.algafoodapi.infraestrutura.repository.jpa;

import io.algafoodapi.business.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepositoryJpa extends JpaRepository<Restaurante, Long> {

  // ----- Derived Queries -----
  Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

  List<Restaurante> findTop2ByNomeContaining(String nome);

  List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

  boolean existsByNome(String nome);

  int countByCozinhaId(Long cozinhaId);

  // ----- JPQL -----
  @Query("from Restaurante as r join fetch r.cozinha left join fetch r.formasPagamento")
  List<Restaurante> findAll();

  @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
  List<Restaurante> consultarPorNomeAndCozinhaId(String nome, @Param("id") Long cozinhaId);

  // ----- JPQL em arquivo XML -----
  List<Restaurante> buscarTodosPorNome(String nome);

  // ----- JPQL em SDJ customizado -----
//  List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

  // ----- JPQL com consultas dinâmicas -----
//  List<Restaurante> findDinamico(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

  // ----- JPQL com consultas dinâmicas pelo Criteria API -----
//  List<Restaurante> findPorCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

  // ----- Native Query -----
}

