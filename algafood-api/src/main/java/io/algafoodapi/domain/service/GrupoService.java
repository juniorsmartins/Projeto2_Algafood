package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.Grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrupoService {

    Grupo cadastrar(Grupo grupo);

    Page<Grupo> pesquisar(Grupo grupo, Pageable paginacao);
}

