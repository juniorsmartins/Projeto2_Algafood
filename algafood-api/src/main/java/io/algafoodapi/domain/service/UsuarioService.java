package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class UsuarioService implements PoliticaCrudBaseService<Usuario, Long> {

    @Override
    public Usuario cadastrar(Usuario entidade) {
        return null;
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

