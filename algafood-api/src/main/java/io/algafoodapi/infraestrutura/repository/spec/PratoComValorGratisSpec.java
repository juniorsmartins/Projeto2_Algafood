package io.algafoodapi.infraestrutura.repository.spec;

import io.algafoodapi.domain.model.Prato;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class PratoComValorGratisSpec implements Specification<Prato> {

    private static final long serialVersionUID = 1L;

    @Override
    public Predicate toPredicate(Root<Prato> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.equal(root.get("valor"), BigDecimal.ZERO);
    }
}
