package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastrarCozinha {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cozinha> listar() {
        TypedQuery<Cozinha> query = entityManager.createQuery("from cozinha", Cozinha.class);
        return query.getResultList();
    }
}
