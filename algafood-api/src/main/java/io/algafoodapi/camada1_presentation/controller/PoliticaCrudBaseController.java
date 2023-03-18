package io.algafoodapi.camada1_presentation.controller;

import io.algafoodapi.camada1_presentation.dto.request.PoliticaAtualizarDtoRequest;
import io.algafoodapi.camada1_presentation.dto.request.PoliticaDtoRequest;
import io.algafoodapi.camada1_presentation.dto.response.PoliticaDtoResponse;
import io.algafoodapi.camada1_presentation.dto.request.PoliticaPesquisarDtoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PoliticaCrudBaseController<R extends PoliticaDtoRequest, S extends PoliticaDtoResponse<I>,
        P extends PoliticaPesquisarDtoRequest<I>, A extends PoliticaAtualizarDtoRequest<I>, I> {

    @PostMapping
    ResponseEntity<S> cadastrar(R dtoRequest, UriComponentsBuilder uriComponentsBuilder);

    @PutMapping
    ResponseEntity<S> atualizar(A dtoRequest);

    @PatchMapping
    ResponseEntity<S> atualizarParcial(I id, Map<String, Object> campos, HttpServletRequest httpServletRequest);

    @GetMapping
    ResponseEntity<Page<S>> pesquisar(P dtoRequest, Pageable paginacao);

    @DeleteMapping(path = {"/{id}"})
    ResponseEntity deletar(I id);
}

