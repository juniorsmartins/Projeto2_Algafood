package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Cidade;
import io.algafoodapi.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
