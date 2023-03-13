package io.algafoodapi.api.controller;

import io.algafoodapi.api.dto.request.UsuarioAtualizarDtoRequest;
import io.algafoodapi.api.dto.request.UsuarioDtoRequest;
import io.algafoodapi.api.dto.request.UsuarioPesquisarDtoRequest;
import io.algafoodapi.api.dto.response.UsuarioDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = {"/v1/usuarios"})
public final class UsuarioController implements PoliticaCrudBaseController<UsuarioDtoRequest, UsuarioDtoResponse,
        UsuarioPesquisarDtoRequest, UsuarioAtualizarDtoRequest, Long> {

    @Override
    public ResponseEntity<UsuarioDtoResponse> cadastrar(UsuarioDtoRequest dtoRequest, UriComponentsBuilder uriComponentsBuilder) {
        return null;
    }

    @Override
    public ResponseEntity<UsuarioDtoResponse> atualizar(UsuarioAtualizarDtoRequest dtoRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UsuarioDtoResponse> atualizarParcial(Long id, Map<String, Object> campos, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Page<UsuarioDtoResponse>> pesquisar(UsuarioPesquisarDtoRequest dtoRequest, Pageable paginacao) {
        return null;
    }

    @Override
    public ResponseEntity deletar(Long id) {
        return null;
    }
}

