package io.algafoodapi.camada3_infraestrutura.repository;

import io.algafoodapi.camada3_infraestrutura.repository.jpa.UsuarioRepositoryJpa;
import io.algafoodapi.camada2_business.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements PoliticaCrudBaseRepository<Usuario, Long>, PoliticaUsuarioRepository {

    @Autowired
    private UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Override
    public Usuario salvar(final Usuario entidade) {
        return this.usuarioRepositoryJpa.save(entidade);
    }

    @Override
    public Page<Usuario> pesquisar(final Example example, final Pageable paginacao) {
        return this.usuarioRepositoryJpa.findAll(example, paginacao);
    }

    @Override
    public void deletar(final Usuario entidade) {
        this.usuarioRepositoryJpa.delete(entidade);
    }

    @Override
    public Optional<Usuario> consultarPorId(final Long id) {
        return this.usuarioRepositoryJpa.findById(id);
    }

    @Override
    public List<Usuario> listar() {
        return this.usuarioRepositoryJpa.findAll();
    }

    @Override
    public Optional<Usuario> consultarPorEmail(String email) {
        return this.usuarioRepositoryJpa.findByEmail(email);
    }
}

