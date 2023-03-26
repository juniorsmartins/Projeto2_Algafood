package io.algafoodapi.business.service;

import io.algafoodapi.presentation.dto.request.UsuarioTrocarSenhaDtoRequest;
import io.algafoodapi.business.core.Constantes;
import io.algafoodapi.business.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.business.exception.http404.UsuarioNaoEncontradoException;
import io.algafoodapi.business.exception.http409.EmailDeUsuarioEmUsoException;
import io.algafoodapi.business.exception.http409.SenhaIncompativelException;
import io.algafoodapi.business.model.Usuario;
import io.algafoodapi.business.ports.GrupoRepository;
import io.algafoodapi.business.utils.ServiceUtils;
import io.algafoodapi.infraestrutura.repository.PoliticaCrudBaseRepository;
import io.algafoodapi.infraestrutura.repository.PoliticaUsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements PoliticaCrudBaseService<Usuario, Long>, PoliticaUsuarioService<UsuarioTrocarSenhaDtoRequest, Long, String> {

    @Autowired
    private PoliticaCrudBaseRepository<Usuario, Long> crudRepository;

    @Autowired
    private PoliticaUsuarioRepository usuarioRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Usuario cadastrar(final Usuario entidade) {

        return Optional.of(entidade)
            .map(this::regraGarantirEmailUnico)
            .map(entity -> this.serviceUtils.regraGarantirNomeUnico(entity, crudRepository))
            .map(this.serviceUtils::capitalizarNome)
            .map(this.crudRepository::salvar)
            .orElseThrow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Usuario atualizar(Usuario entidade) {
        var idUsuario = entidade.getId();

        return this.crudRepository.consultarPorId(idUsuario)
            .map(this::validarIdsDeRelacionamentos)
            .map(this::regraGarantirEmailUnico)
            .map(entity -> this.serviceUtils.regraGarantirNomeUnico(entity, crudRepository))
            .map(this.serviceUtils::capitalizarNome)
            .map(entity -> {
                BeanUtils.copyProperties(entidade, entity, "id", "senha");
                entity.setDataCadastro(OffsetDateTime.now());
                return entity;
            })
            .orElseThrow(() -> new UsuarioNaoEncontradoException(idUsuario));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Usuario> pesquisar(final Usuario entidade, final Pageable paginacao) {

        var condicoesDePesquisa = this.serviceUtils.criarCondicoesDePesquisa(entidade);
        return this.crudRepository.pesquisar(condicoesDePesquisa, paginacao);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void deletar(final Long id) {

        this.crudRepository.consultarPorId(id)
            .map(user -> {
                this.crudRepository.deletar(user);
                return true;
            })
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public String trocarSenha(final UsuarioTrocarSenhaDtoRequest dtoRequest) {
        var idUsuario = dtoRequest.getId();

        this.crudRepository.consultarPorId(idUsuario)
            .map(user -> {
                if (!user.getSenha().equals(dtoRequest.getSenhaAtual())) {
                    throw new SenhaIncompativelException();
                }
                user.setSenha(dtoRequest.getSenhaNova());
                return user;
            })
            .orElseThrow(() -> new UsuarioNaoEncontradoException(idUsuario));

        return Constantes.SENHA_ALTERADA_COM_SUCESSO;
    }

    private Usuario regraGarantirEmailUnico(final Usuario usuario) {
        var emailParaVerificar = usuario.getEmail();
        var emailExiste = this.usuarioRepository.consultarPorEmail(emailParaVerificar);

        if (emailExiste.isPresent() && usuario.getId() != emailExiste.get().getId()) {
            throw new EmailDeUsuarioEmUsoException(emailParaVerificar);
        }
        return usuario;
    }

    private Usuario validarIdsDeRelacionamentos(final Usuario usuario) {

        return Optional.of(usuario)
            .map(user -> {
                user.getGrupos().forEach(grupo -> {
                    var idGrupo = grupo.getId();
                    this.grupoRepository.consultarPorId(idGrupo)
                        .orElseThrow(() -> new RequisicaoMalFormuladaException(String.format(Constantes.GRUPO_NAO_ENCONTRADO, idGrupo)));
                });
                return user;
            })
            .orElseThrow();
    }
}
