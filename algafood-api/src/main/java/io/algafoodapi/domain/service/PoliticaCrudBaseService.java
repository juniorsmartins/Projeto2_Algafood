package io.algafoodapi.domain.service;

import io.algafoodapi.domain.model.PoliticaEntidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PoliticaCrudBaseService<E extends PoliticaEntidade<I>, I> {

    E cadastrar(E entidade);

    E atualizar(E entidade);

    E atualizarParcial(I id, Map<String, Object> campos, HttpServletRequest httpServletRequest);

    Page<E> pesquisar(E entidade, Pageable paginacao);

    void deletar(I id);
}

