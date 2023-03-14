package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.Usuario;
import io.algafoodapi.domain.utils.ServiceUtils;
import io.algafoodapi.infraestrutura.repository.PoliticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService implements PoliticaCrudBaseService<Usuario, Long> {

    @Autowired
    private PoliticaRepository<Usuario, Long> repository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Override
    public Usuario cadastrar(Usuario entidade) {

        return Optional.of(entidade)
            .map(entity -> this.serviceUtils.regraGarantirNomeUnico(entity, repository))
            .map(this.serviceUtils::capitalizarNome)
            .map(this.repository::salvar)
            .orElseThrow();
    }

    @Override
    public Usuario atualizar(Usuario entidade) {
        return null;
    }

    @Override
    public Usuario atualizarParcial(Long id, Map<String, Object> campos, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public Page<Usuario> pesquisar(Usuario entidade, Pageable paginacao) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}

