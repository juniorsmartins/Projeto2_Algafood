package io.algafoodapi.infraestrutura.repository;

import io.algafoodapi.business.model.PoliticaEntidade;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PoliticaCrudBaseRepository<E extends PoliticaEntidade<I>, I> {

    E salvar(E entidade);

    Page<E> pesquisar(Example<E> example, Pageable paginacao);

    void deletar(E entidade);

    Optional<E> consultarPorId(I id);

    List<E> listar();
}

