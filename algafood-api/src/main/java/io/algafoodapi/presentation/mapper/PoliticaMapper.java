package io.algafoodapi.presentation.mapper;

import io.algafoodapi.presentation.dto.request.PoliticaAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.PoliticaDtoRequest;
import io.algafoodapi.presentation.dto.response.PoliticaDtoResponse;
import io.algafoodapi.presentation.dto.request.PoliticaPesquisarDtoRequest;
import io.algafoodapi.business.model.PoliticaEntidade;
import org.springframework.data.domain.Page;

public interface PoliticaMapper<R extends PoliticaDtoRequest, S extends PoliticaDtoResponse<I>,
        P extends PoliticaPesquisarDtoRequest<I>, A extends PoliticaAtualizarDtoRequest<I>,
        E extends PoliticaEntidade<I>, I> {


    E converterDtoRequestParaEntidade(R dtoRequest);

    S converterEntidadeParaDtoResponse(E entidade);

    E converterPesquisarDtoRequestParaEntidade(P pesquisarDtoRequest);

    E converterAtualizarDtoRequestParaEntidade(A atualizarDtoRequest);

    Page<S> converterPaginaDeEntidadeParaPaginaDeDtoResponse(Page<E> entidades);
}
