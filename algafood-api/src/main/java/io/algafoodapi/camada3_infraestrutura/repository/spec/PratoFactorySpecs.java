package io.algafoodapi.camada3_infraestrutura.repository.spec;

import io.algafoodapi.camada2_business.model.Prato;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PratoFactorySpecs {

    public static Specification<Prato> comValorGratis() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("valor"), BigDecimal.ZERO));
    }

    public static Specification<Prato> comNomeSemelhante(String nome) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
    }
}
