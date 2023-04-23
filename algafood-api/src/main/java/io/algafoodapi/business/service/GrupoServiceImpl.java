package io.algafoodapi.business.service;

import io.algafoodapi.business.exception.ConstantesDeErro;
import io.algafoodapi.business.exception.http404.GrupoNaoEncontradoException;
import io.algafoodapi.business.exception.http409.NomeDeGrupoEmUsoException;
import io.algafoodapi.business.model.Grupo;
import io.algafoodapi.business.model.Permissao;
import io.algafoodapi.business.utils.ServiceUtils;
import io.algafoodapi.infraestrutura.repository.PoliticaCrudBaseRepository;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class GrupoServiceImpl implements PoliticaCrudBaseService<Grupo, Long>, PoliticaGrupoService<Long> {

    @Autowired
    private PoliticaCrudBaseRepository<Grupo, Long> repository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private ConstantesDeErro constantesDeErro;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Grupo cadastrar(Grupo grupo) {

        return Optional.of(grupo)
            .map(this::regraGarantirNomeUnico)
            .map(this::regraCapitalizarNome)
            .map(this.repository::salvar)
            .orElseThrow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Grupo atualizar(Grupo grupo) {
        var id = grupo.getId();

        return this.repository.consultarPorId(id)
            .map(grupoDoDatabase -> {
                this.regraGarantirNomeUnico(grupo);
                BeanUtils.copyProperties(grupo, grupoDoDatabase, "id");
                return grupoDoDatabase;
            })
            .map(this::regraCapitalizarNome)
            .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Grupo> pesquisar(final Grupo grupo, final Pageable paginacao) {

        var condicoesDePesquisa = this.criarCondicoesParaPesquisar(grupo);
        var resultadoDaPesquisa = this.repository.pesquisar(condicoesDePesquisa, paginacao);

        if (resultadoDaPesquisa.isEmpty()) {
            throw new GrupoNaoEncontradoException(constantesDeErro.NENHUM_RECURSO_ENCONTRADO);
        }

        return resultadoDaPesquisa;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void deletar(final Long id) {

        this.repository.consultarPorId(id)
            .map(grupo -> {
                this.repository.deletar(grupo);
                return true;
            })
            .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Override
    public Set<Permissao> consultarPermissoesPorIdDeGrupo(final Long id) {

        return this.repository.consultarPorId(id)
            .map(Grupo::getPermissoes)
            .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    private Grupo regraCapitalizarNome(Grupo grupo) {
        var nomeParaCapitalizar = grupo.getNome();
        var nomeCapitalizado = WordUtils.capitalizeFully(nomeParaCapitalizar);
        grupo.setNome(nomeCapitalizado);
        return grupo;
    }

    private Grupo regraGarantirNomeUnico(Grupo grupo) {
        var nomeDeEntradaParaPadronizar = grupo.getNome();
        var nomeDeEntradaPadronizado = this.serviceUtils.padronizarNome(nomeDeEntradaParaPadronizar);

        var existeNomeIgual = this.repository.listar()
            .stream()
            .filter(grupoDoDatabase -> grupoDoDatabase.getId() != grupo.getId())
            .map(Grupo::getNome)
            .map(this.serviceUtils::padronizarNome)
            .anyMatch(nomeDoDatabasePadronizado -> nomeDoDatabasePadronizado.equals(nomeDeEntradaPadronizado));

        if (existeNomeIgual) {
            throw new NomeDeGrupoEmUsoException(nomeDeEntradaParaPadronizar);
        }

        return grupo;
    }

    private Example criarCondicoesParaPesquisar(Grupo grupo) {
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withIgnoreNullValues()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(grupo, matcher);
    }


}

