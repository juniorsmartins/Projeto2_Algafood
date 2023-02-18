package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.CidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.http404.EstadoNaoEncontradoException;
import io.algafoodapi.domain.exception.http409.CidadeEmUsoException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.repository.CidadeRepository;
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
import java.util.stream.Collectors;

@Service
public class CidadeService {

    public static final String NÃO_EXISTEM_CIDADES_CADASTRADAS = "Não há cidades cadastradas.";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cidade criar(Cidade cidade) {

        try {
            var estado = this.estadoService.consultarPorId(cidade.getEstado().getId());
            cidade.setEstado(estado);

        } catch (EstadoNaoEncontradoException naoEncontradoException) {
            throw new RequisicaoMalFormuladaException(naoEncontradoException.getMessage(), naoEncontradoException);
        }

        return this.cidadeRepository.saveAndFlush(cidade);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Cidade atualizar(Long id, Cidade cidadeAtual) {

        var cidade = this.consultarPorId(id);

        try {
            var estado = this.estadoService.consultarPorId(cidadeAtual.getEstado().getId());
            cidadeAtual.setEstado(estado);

        } catch (EstadoNaoEncontradoException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage(), naoEncontradaException);
        }

        BeanUtils.copyProperties(cidadeAtual, cidade, "id");

        return this.cidadeRepository.saveAndFlush(cidade);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(Long id) {

        try {
            this.cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new CidadeNaoEncontradaException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CidadeEmUsoException(id);
        }
    }

    @Transactional(readOnly = true)
    public Cidade consultarPorId(Long id) {

        return this.cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    @Transactional(readOnly = true)
    public List<Cidade> listar() {

        var cidades = this.cidadeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cidade::getId).reversed())
                .collect(Collectors.toList());

        if(cidades.isEmpty())
            throw new CidadeNaoEncontradaException(NÃO_EXISTEM_CIDADES_CADASTRADAS);

        return cidades;
    }
}

