package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    public static final String NÃO_ENCONTRADA_CIDADE_COM_ID = "Não encontrada cidade com código %d.";
    public static final String PROIBIDO_APAGAR_CIDADE_EM_USO_COM_ID = "Proibido apagar cidade em uso (ID: %d).";
    public static final String NÃO_EXISTEM_CIDADES_CADASTRADAS = "Não há cidades cadastradas.";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    public Cidade criar(Cidade cidade) {

        try {
            var estado = this.estadoService.consultarPorId(cidade.getEstado().getId());
            cidade.setEstado(estado);

        } catch (EstadoNaoEncontradoException naoEncontradoException) {
            throw new RequisicaoMalFormuladaException(naoEncontradoException.getMessage());
        }

        return this.cidadeRepository.saveAndFlush(cidade);
    }

    public Cidade atualizar(Long id, Cidade cidadeAtual) {

        var cidade = this.consultarPorId(id);

        try {
            var estado = this.estadoService.consultarPorId(cidadeAtual.getEstado().getId());
            cidadeAtual.setEstado(estado);

        } catch (EstadoNaoEncontradoException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage());
        }

        BeanUtils.copyProperties(cidadeAtual, cidade, "id");

        return this.cidadeRepository.saveAndFlush(cidade);
    }

    public void excluirPorId(Long id) {

        try {
            this.cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format(NÃO_ENCONTRADA_CIDADE_COM_ID, id));

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new EntidadeEmUsoException(String.format(PROIBIDO_APAGAR_CIDADE_EM_USO_COM_ID, id));
        }
    }

    public Cidade consultarPorId(Long id) {

        return this.cidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                    String.format(NÃO_ENCONTRADA_CIDADE_COM_ID, id)));
    }

    public List<Cidade> listar() {

        var cidades = this.cidadeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Cidade::getId).reversed())
                .collect(Collectors.toList());

        if(cidades.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format(NÃO_EXISTEM_CIDADES_CADASTRADAS));

        return cidades;
    }
}

