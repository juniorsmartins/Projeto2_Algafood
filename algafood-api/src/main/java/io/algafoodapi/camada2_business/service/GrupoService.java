package io.algafoodapi.camada2_business.service;

import io.algafoodapi.camada2_business.model.Grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrupoService {

    Grupo cadastrar(Grupo grupo);

    Grupo atualizar(Grupo grupo);

    Page<Grupo> pesquisar(Grupo grupo, Pageable paginacao);

    void apagarPorId(Long id);
}

