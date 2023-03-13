package io.algafoodapi.api.mapper;

import io.algafoodapi.api.dto.request.UsuarioAtualizarDtoRequest;
import io.algafoodapi.api.dto.request.UsuarioDtoRequest;
import io.algafoodapi.api.dto.request.UsuarioPesquisarDtoRequest;
import io.algafoodapi.api.dto.response.UsuarioDtoResponse;
import io.algafoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
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
    public Page<UsuarioDtoResponse> converterPaginaDeEntidadeParaPaginaDeDtoResponse(Page<Usuario> entidades) {
        return entidades.map(this::converterEntidadeParaDtoResponse);
    }
}

