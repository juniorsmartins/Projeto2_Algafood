package io.algafoodapi.camada1_presentation.controller;

import io.algafoodapi.camada1_presentation.dto.request.UsuarioAtualizarDtoRequest;
import io.algafoodapi.camada1_presentation.dto.request.UsuarioDtoRequest;
import io.algafoodapi.camada1_presentation.dto.request.UsuarioPesquisarDtoRequest;
import io.algafoodapi.camada1_presentation.dto.request.UsuarioTrocarSenhaDtoRequest;
import io.algafoodapi.camada1_presentation.dto.response.UsuarioDtoResponse;
import io.algafoodapi.camada1_presentation.mapper.PoliticaMapper;
import io.algafoodapi.camada2_business.model.Usuario;
import io.algafoodapi.camada2_business.service.PoliticaCrudBaseService;
import io.algafoodapi.camada2_business.service.PoliticaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/v1/usuarios"})
public final class UsuarioControllerImpl implements PoliticaCrudBaseController<UsuarioDtoRequest, UsuarioDtoResponse,
    UsuarioPesquisarDtoRequest, UsuarioAtualizarDtoRequest, Long>, PoliticaUsuarioController<UsuarioTrocarSenhaDtoRequest, Long, String> {

    @Autowired
    private PoliticaMapper<UsuarioDtoRequest, UsuarioDtoResponse, UsuarioPesquisarDtoRequest,
                UsuarioAtualizarDtoRequest, Usuario, Long> mapper;

    @Autowired
    private PoliticaCrudBaseService<Usuario, Long> usuarioCrudservice;

    @Autowired
    private PoliticaUsuarioService<UsuarioTrocarSenhaDtoRequest, Long, String> usuarioService;

    @Override
    public ResponseEntity<UsuarioDtoResponse> cadastrar(@RequestBody @Valid final UsuarioDtoRequest dtoRequest,
                                                        final UriComponentsBuilder uriComponentsBuilder) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterDtoRequestParaEntidade)
            .map(this.usuarioCrudservice::cadastrar)
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
            .map(this.usuarioCrudservice::atualizar)
            .map(this.mapper::converterEntidadeParaDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity<Page<UsuarioDtoResponse>> pesquisar(final UsuarioPesquisarDtoRequest dtoRequest,
      @PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 20) final Pageable paginacao) {

        var response = Optional.of(dtoRequest)
            .map(this.mapper::converterPesquisarDtoRequestParaEntidade)
            .map(entidade -> this.usuarioCrudservice.pesquisar(entidade, paginacao))
            .map(this.mapper::converterPaginaDeEntidadeParaPaginaDeDtoResponse)
            .orElseThrow();

        return ResponseEntity
            .ok()
            .body(response);
    }

    @Override
    public ResponseEntity deletar(@PathVariable(name = "id") final Long id) {

        this.usuarioCrudservice.deletar(id);

        return ResponseEntity
            .noContent()
            .build();
    }

    @Override
    public ResponseEntity<String> trocarSenha(@RequestBody @Valid final UsuarioTrocarSenhaDtoRequest dtoRequest) {

        var response = this.usuarioService.trocarSenha(dtoRequest);

        return ResponseEntity
            .ok()
            .body(response);
    }
}

