package io.algafoodapi.domain.ports;

import io.algafoodapi.domain.model.Grupo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GrupoRepository {

    Grupo salvar(Grupo grupo);

    List<Grupo> listar();

    Page<Grupo> pesquisar(Example example, Pageable paginacao);
}

