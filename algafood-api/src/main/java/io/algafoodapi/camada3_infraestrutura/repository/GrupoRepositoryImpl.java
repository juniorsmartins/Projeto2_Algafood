package io.algafoodapi.camada3_infraestrutura.repository;

import io.algafoodapi.camada2_business.model.Grupo;
import io.algafoodapi.camada2_business.ports.GrupoRepository;
import io.algafoodapi.camada3_infraestrutura.repository.jpa.GrupoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GrupoRepositoryImpl implements GrupoRepository {

    @Autowired
    private GrupoRepositoryJpa grupoRepositoryJpa;

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

    @Override
    public Optional<Grupo> consultarPorId(Long id) {
        return this.grupoRepositoryJpa.findById(id);
    }

    @Override
    public void apagar(Grupo grupo) {
        this.grupoRepositoryJpa.delete(grupo);
    }
}

