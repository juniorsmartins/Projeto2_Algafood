package io.algafoodapi.domain.service;

import io.algafoodapi.domain.core.Constantes;
import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.CidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http409.CidadeEmUsoException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.repository.CidadeRepository;
import io.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CidadeService(final CidadeRepository cidadeRepository, final EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cidade criar(Cidade cidade) {

        this.validarEstado(cidade);
        return this.cidadeRepository.saveAndFlush(cidade);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cidade atualizar(final Long idCidade, Cidade cidade) {

        this.validarEstado(cidade);
        var cidadeParaAtualizar = this.consultarPorId(idCidade);
        BeanUtils.copyProperties(cidade, cidadeParaAtualizar, "id");
        return this.cidadeRepository.saveAndFlush(cidadeParaAtualizar);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(final Long id) {

        try {
            this.cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new CidadeNaoEncontradaException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CidadeEmUsoException(id); // TODO - BUG - Não captura e não lança
        }
    }

    @Transactional(readOnly = true)
    public Cidade consultarPorId(final Long id) {

        return this.cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    @Transactional(readOnly = true)
    public List<Cidade> listar() {

        var cidades = this.cidadeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cidade::getId).reversed())
                .toList();

        if (cidades.isEmpty()) {
            throw new CidadeNaoEncontradaException(Constantes.NÃO_EXISTEM_CIDADES_CADASTRADAS);
        }

        return cidades;
    }

    private void validarEstado(Cidade cidade) {
        var idEstado = cidade.getEstado().getId();
        var estado = this.estadoRepository.findById(idEstado)
                .orElseThrow(() ->
                        new RequisicaoMalFormuladaException(String.format(Constantes.ESTADO_NAO_ENCONTRADO, idEstado)));
        cidade.setEstado(estado);
    }
}

