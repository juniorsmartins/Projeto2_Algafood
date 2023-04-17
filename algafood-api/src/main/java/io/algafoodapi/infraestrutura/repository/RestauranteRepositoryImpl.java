package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Restaurante;
import io.algafoodapi.infraestrutura.repository.jpa.RestauranteRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteRepositoryImpl implements PoliticaCrudBaseRepository<Restaurante, Long>, PoliticaRestauranteRepository<Long> {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private RestauranteRepositoryJpa restauranteRepositoryJpa;

    // JPQL com SDJ customizado
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var jpql = "from Restaurante where nome like :nome "
                + "and taxaFrete between :taxaInicial and :taxaFinal";

        return this.manager.createQuery(jpql, Restaurante.class)
            .setParameter("nome", "%" + nome + "%")
            .setParameter("taxaInicial", taxaFreteInicial)
            .setParameter("taxaFinal",taxaFreteFinal)
            .getResultList();
    }

    // JPQL com consultas dinâmicas
    public List<Restaurante> findDinamico(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");

        var parametros = new HashMap<String, Object>();

        if(StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if(taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if(taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal");
            parametros.put("taxaFinal",taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = this.manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();
    }

    @Override
    public Optional<Restaurante> findById(final Long id) {
        return this.restauranteRepositoryJpa.findById(id);
    }

    @Override
    public List<Restaurante> buscarTodosPorNome(final String nome) {
        return this.restauranteRepositoryJpa.buscarTodosPorNome(nome);
    }

    // JPQL com consultas dinâmicas pelo Criteria API
//    @Override
//    public List<Restaurante> findPorCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//
//        CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();
//        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
//        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);
//
//        var predicates = new ArrayList<Predicate>();
//
//        if(StringUtils.hasLength(nome))
//            predicates.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
//
//        if(taxaFreteInicial != null)
//            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
//
//        if(taxaFreteFinal != null)
//            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
//
//        criteriaQuery.where(predicates.toArray(new Predicate[0]));
//
//        TypedQuery<Restaurante> typedQuery = this.manager.createQuery(criteriaQuery);
//        return typedQuery.getResultList();
//    }

    @Override
    public Restaurante salvar(final Restaurante entidade) {
        return this.restauranteRepositoryJpa.save(entidade);
    }

    @Override
    public Page<Restaurante> pesquisar(final Example<Restaurante> example, final Pageable paginacao) {
        return this.restauranteRepositoryJpa.findAll(example, paginacao);
    }

    @Override
    public void deletar(final Restaurante entidade) {
        this.restauranteRepositoryJpa.delete(entidade);
    }

    @Override
    public Optional<Restaurante> consultarPorId(final Long id) {
        return this.restauranteRepositoryJpa.findById(id);
    }

    @Override
    public List<Restaurante> listar() {
        return this.restauranteRepositoryJpa.findAll();
    }
}
