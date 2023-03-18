package io.algafoodapi.camada1_presentation.controller;

import io.algafoodapi.camada1_presentation.dto.request.UsuarioAtualizarDtoRequest;
import io.algafoodapi.camada1_presentation.dto.request.UsuarioDtoRequest;
import io.algafoodapi.camada1_presentation.dto.request.UsuarioPesquisarDtoRequest;
import io.algafoodapi.camada1_presentation.dto.response.UsuarioDtoResponse;
import io.algafoodapi.camada1_presentation.mapper.PoliticaMapper;
import io.algafoodapi.camada2_business.model.Usuario;
import io.algafoodapi.camada2_business.service.PoliticaCrudBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/v1/usuarios"})
public final class UsuarioController implements PoliticaCrudBaseController<UsuarioDtoRequest, UsuarioDtoResponse,
    UsuarioPesquisarDtoRequest, UsuarioAtualizarDtoRequest, Long> {

    @Autowired
    private PoliticaMapper<UsuarioDtoRequest, UsuarioDtoResponse, UsuarioPesquisarDtoRequest,
                UsuarioAtualizarDtoRequest, Usuario, Long> mapper;

    @Autowired
    private PoliticaCrudBaseService<Usuario, Long> service;

    @Override
    public ResponseEntity<UsuarioDtoResponse> cadastrar(@RequestBody @Valid final UsuarioDtoRequest dtoRequest,
                                                        final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterDtoRequestParaEntidade)
            .map(this.service::cadastrar)
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .created(uriComponentsBuilder
                .path("/v1/usuarios/{id}")
                .buildAndExpand(response.getId())
                .toUri())
            .body(response);
    }

    @Override
    public ResponseEntity<UsuarioDtoResponse> atualizar(@RequestBody @Valid final UsuarioAtualizarDtoRequest dtoRequest) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterAtualizarDtoRequestParaEntidade)
            .map(this.service::atualizar)
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<UsuarioDtoResponse> atualizarParcial(final Long id, final Map<String, Object> campos, final HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Page<UsuarioDtoResponse>> pesquisar(final UsuarioPesquisarDtoRequest dtoRequest,
      @PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 20) final Pageable paginacao) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterPesquisarDtoRequestParaEntidade)
            .map(entidade -> this.service.pesquisar(entidade, paginacao))
            .map(this.mapper::converterPaginaDeEntidadeParaPaginaDeDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity deletar(final Long id) {
        return null;
    }
}

