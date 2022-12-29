package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService estadoService;

    public Cidade salvar(Cidade cidade) throws EntidadeNaoEncontradaException {

        var estadoId = cidade.getEstado().getId();
        var estado = this.estadoService.buscar(estadoId);
        cidade.setEstado(estado);

        return this.cidadeRepository.salvar(cidade);
    }

    public Cidade atualizar(Long id, Cidade cidadeAtual) throws EntidadeNaoEncontradaException, RequisicaoMalFormuladaException {

        var cidade = this.buscar(id);

        Estado estado;
        try {
            estado = this.estadoService.buscar(cidadeAtual.getEstado().getId());
        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage());
        }

        cidadeAtual.setEstado(estado);
        BeanUtils.copyProperties(cidadeAtual, cidade, "id");

        return this.cidadeRepository.salvar(cidade);
    }



    public Cidade buscar(Long id) {

        var cidade = this.cidadeRepository.buscar(id);

        if(cidade == null)
            throw new EntidadeNaoEncontradaException("""
                    Não encontrada cidade com código %d.""".formatted(id));

        return cidade;
    }

    public List<Cidade> listar() {

        var cidades = this.cidadeRepository.listar();

        if(cidades.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há cidades cadastradas no banco de dados."));

        return cidades;
    }
}
