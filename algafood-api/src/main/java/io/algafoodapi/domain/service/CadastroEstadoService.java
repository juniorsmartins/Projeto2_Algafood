package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return this.estadoRepository.salvar(estado);
    }

    public List<Estado> listar() {
        var estados = this.estadoRepository.listar();

        if(estados.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há estados cadastrados no banco de dados."));

        return estados;
    }
}
