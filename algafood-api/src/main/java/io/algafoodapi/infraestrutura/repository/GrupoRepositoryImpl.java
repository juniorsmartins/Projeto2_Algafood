package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.domain.model.Grupo;
import io.algafoodapi.domain.ports.GrupoRepository;
import io.algafoodapi.infraestrutura.repository.jpa.GrupoRepositoryJpa;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GrupoRepositoryImpl implements GrupoRepository {

    private final GrupoRepositoryJpa grupoRepositoryJpa;

    public GrupoRepositoryImpl(final GrupoRepositoryJpa grupoRepositoryJpa) {
        this.grupoRepositoryJpa = grupoRepositoryJpa;
    }

    @Override
    public Grupo salvar(Grupo grupo) {
        return this.grupoRepositoryJpa.save(grupo);
    }

    @Override
    public List<Grupo> listar() {
        return this.grupoRepositoryJpa.findAll();
    }

    @Override
    public Page<Grupo> pesquisar(Example example, Pageable paginacao) {
        return this.grupoRepositoryJpa.findAll(example, paginacao);
    }
}

