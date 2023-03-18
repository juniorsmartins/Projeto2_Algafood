package io.algafoodapi.camada2_business.service;

import io.algafoodapi.camada2_business.exception.http404.UsuarioNaoEncontradoException;
import io.algafoodapi.camada2_business.exception.http409.EmailDeUsuarioEmUsoException;
import io.algafoodapi.camada2_business.model.Usuario;
import io.algafoodapi.camada2_business.utils.ServiceUtils;
import io.algafoodapi.camada3_infraestrutura.repository.PoliticaCrudBaseRepository;
import io.algafoodapi.camada3_infraestrutura.repository.PoliticaUsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService implements PoliticaCrudBaseService<Usuario, Long> {

    @Autowired
    private PoliticaCrudBaseRepository<Usuario, Long> repository;

    @Autowired
    private PoliticaUsuarioRepository usuarioRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Usuario cadastrar(final Usuario entidade) {

        return Optional.of(entidade)
            .map(this::regraGarantirEmailUnico)
            .map(entity -> this.serviceUtils.regraGarantirNomeUnico(entity, repository))
            .map(this.serviceUtils::capitalizarNome)
            .map(this.repository::salvar)
            .orElseThrow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Usuario atualizar(Usuario entidade) {
        var idUsuario = entidade.getId();

        return this.repository.consultarPorId(idUsuario)
            .map(this::regraGarantirEmailUnico)
            .map(entity -> this.serviceUtils.regraGarantirNomeUnico(entity, repository))
            .map(this.serviceUtils::capitalizarNome)
            .map(entity -> {
                BeanUtils.copyProperties(entidade, entity, "id");
                entity.setDataCadastro(OffsetDateTime.now());
                return entity;
            })
            .orElseThrow(() -> new UsuarioNaoEncontradoException(idUsuario));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Usuario atualizarParcial(final Long id, final Map<String, Object> campos, final HttpServletRequest httpServletRequest) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Usuario> pesquisar(final Usuario entidade, final Pageable paginacao) {

        var condicoesDePesquisa = this.serviceUtils.criarCondicoesDePesquisa(entidade);
        return this.repository.pesquisar(condicoesDePesquisa, paginacao);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void deletar(final Long id) {

        this.repository.consultarPorId(id)
            .map(user -> {
                this.repository.deletar(user);
                return true;
            })
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    private Usuario regraGarantirEmailUnico(final Usuario usuario) {
        var emailParaVerificar = usuario.getEmail();
        var emailExiste = this.usuarioRepository.consultarPorEmail(emailParaVerificar);

        if (emailExiste.isPresent() && usuario.getId() != emailExiste.get().getId()) {
            throw new EmailDeUsuarioEmUsoException(emailParaVerificar);
        }
        return usuario;
    }
}

