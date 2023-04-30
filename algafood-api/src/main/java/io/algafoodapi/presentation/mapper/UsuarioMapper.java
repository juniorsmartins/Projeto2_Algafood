package io.algafoodapi.presentation.mapper;

import io.algafoodapi.business.model.Usuario;
import io.algafoodapi.presentation.dto.request.UsuarioAtualizarDtoRequest;
import io.algafoodapi.presentation.dto.request.UsuarioDtoRequest;
import io.algafoodapi.presentation.dto.request.UsuarioPesquisarDtoRequest;
import io.algafoodapi.presentation.dto.response.UsuarioDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class UsuarioMapper implements PoliticaMapper<UsuarioDtoRequest, UsuarioDtoResponse,
    UsuarioPesquisarDtoRequest, UsuarioAtualizarDtoRequest, Usuario, Long> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Usuario converterDtoRequestParaEntidade(UsuarioDtoRequest dtoRequest) {
        return this.modelMapper.map(dtoRequest, Usuario.class);
    }

    @Override
    public UsuarioDtoResponse converterEntidadeParaDtoResponse(Usuario entidade) {
        return this.modelMapper.map(entidade, UsuarioDtoResponse.class);
    }

    @Override
    public Usuario converterPesquisarDtoRequestParaEntidade(UsuarioPesquisarDtoRequest pesquisarDtoRequest) {
        return this.modelMapper.map(pesquisarDtoRequest, Usuario.class);
    }

    @Override
    public Usuario converterAtualizarDtoRequestParaEntidade(UsuarioAtualizarDtoRequest atualizarDtoRequest) {
        return this.modelMapper.map(atualizarDtoRequest, Usuario.class);
    }

    @Override
    public Page<UsuarioDtoResponse> converterPaginaDeEntidadesParaPaginaDeDtoResponse(Page<Usuario> entidades) {
        return entidades.map(this::converterEntidadeParaDtoResponse);
    }

    @Override
    public List<UsuarioDtoResponse> converterListaDeEntidadesParaListaDeDtoResponse(List<Usuario> entidades) {
        return entidades.stream()
            .map(this::converterEntidadeParaDtoResponse)
            .toList();
    }
}

