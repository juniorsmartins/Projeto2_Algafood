package io.algafoodapi.business.service;

import io.algafoodapi.business.core.Constantes;
import io.algafoodapi.business.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.business.exception.http404.CidadeNaoEncontradaException;
import io.algafoodapi.business.exception.http409.CidadeEmUsoException;
import io.algafoodapi.business.model.Cidade;
import io.algafoodapi.business.ports.EstadoRepository;
import io.algafoodapi.infraestrutura.repository.jpa.CidadeRepositoryJpa;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CidadeRepositoryJpa cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Cidade cadastrar(Cidade cidade) {

        this.validarEstado(cidade);
        return this.cidadeRepository.save(cidade);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Cidade atualizar(final String codigoCidade, Cidade cidade) {

        this.validarEstado(cidade);

        return this.cidadeRepository.findByCodigo(codigoCidade)
                .map(city -> {
                    BeanUtils.copyProperties(cidade, city, "id", "codigo");
                    return city;
                })
                .orElseThrow(() -> new CidadeNaoEncontradaException(codigoCidade));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorCodigo(final String codigoCidade) {

        try {
            this.cidadeRepository.deleteByCodigo(codigoCidade);
            this.cidadeRepository.flush(); // Manda descarregar as operações antes do commit da transação

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new CidadeNaoEncontradaException(codigoCidade);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CidadeEmUsoException(codigoCidade);
        }
    }

    @Transactional(readOnly = true)
    public Cidade consultarPorCodigo(final String codigoCidade) {

        return this.cidadeRepository.findByCodigo(codigoCidade)
                .orElseThrow(() -> new CidadeNaoEncontradaException(codigoCidade));
    }

    @Transactional(readOnly = true)
    public List<Cidade> listar() {

        var cidades = this.cidadeRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Cidade::getId).reversed())
            .toList();

        if (cidades.isEmpty()) {
            throw new CidadeNaoEncontradaException(Constantes.NÃO_EXISTEM_CIDADES);
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

