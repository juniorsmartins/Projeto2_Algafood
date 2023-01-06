package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    public Cidade salvar(Cidade cidade) throws EntidadeNaoEncontradaException {

        var estadoId = cidade.getEstado().getId();
        var estado = this.estadoService.consultarPorId(estadoId);
        cidade.setEstado(estado);

        return this.cidadeRepository.saveAndFlush(cidade);
    }

    public Cidade atualizar(Long id, Cidade cidadeAtual) throws EntidadeNaoEncontradaException, RequisicaoMalFormuladaException {

        var cidade = this.consultarPorId(id);

        Estado estado;
        try {
            estado = this.estadoService.consultarPorId(cidadeAtual.getEstado().getId());
        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage());
        }

        cidadeAtual.setEstado(estado);
        BeanUtils.copyProperties(cidadeAtual, cidade, "id");

        return this.cidadeRepository.saveAndFlush(cidade);
    }

    public void excluirPorId(Long id) {

        try {
            this.cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException("""
                    Não encontrada cidade com código %d.""".formatted(id));
        }
    }

    public Cidade consultarPorId(Long id) {

        return this.cidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("""
                    Não encontrada cidade com código %d.""".formatted(id)));
    }

    public List<Cidade> buscarTodos() {

        var cidades = this.cidadeRepository.findAll();

        if(cidades.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há cidades cadastradas no banco de dados."));

        return cidades;
    }
}
