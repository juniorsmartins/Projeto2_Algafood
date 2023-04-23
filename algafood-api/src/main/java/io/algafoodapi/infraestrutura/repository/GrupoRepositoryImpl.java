package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.Grupo;
import io.algafoodapi.infraestrutura.repository.jpa.GrupoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GrupoRepositoryImpl implements PoliticaCrudBaseRepository<Grupo, Long> {

    @Autowired
    private GrupoRepositoryJpa grupoRepositoryJpa;

    @Override
    public Grupo salvar(Grupo grupo) {
        return this.grupoRepositoryJpa.save(grupo);
    }

    @Override
    public Page<Grupo> pesquisar(final Example<Grupo> example, final Pageable paginacao) {
        return this.grupoRepositoryJpa.findAll(example, paginacao);
    }

    @Override
    public void deletar(Grupo entidade) {
        this.grupoRepositoryJpa.delete(entidade);
    }

    @Override
    public List<Grupo> listar() {
        return this.grupoRepositoryJpa.findAll();
    }

    @Override
    public Optional<Grupo> consultarPorId(final Long id) {
        return this.grupoRepositoryJpa.findById(id);
    }
}

