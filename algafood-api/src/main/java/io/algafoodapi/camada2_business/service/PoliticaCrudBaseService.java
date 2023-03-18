package io.algafoodapi.camada2_business.service;

import io.algafoodapi.camada2_business.model.PoliticaEntidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PoliticaCrudBaseService<E extends PoliticaEntidade<I>, I> {

    E cadastrar(E entidade);

    E atualizar(E entidade);

    Page<E> pesquisar(E entidade, Pageable paginacao);

    void deletar(I id);
}

