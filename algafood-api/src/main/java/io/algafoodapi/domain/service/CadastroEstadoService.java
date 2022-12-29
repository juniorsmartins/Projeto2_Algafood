package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return this.estadoRepository.salvar(estado);
    }

    public void excluir(Long id) {

        try {
            this.estadoRepository.remover(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrado estado com código %d.", id));

        } catch (DataIntegrityViolationException violationException) {
            throw new EntidadeEmUsoException(String.format("Não pode ser removida cozinha com código %d, pois está em uso.", id));
        }
    }

    public List<Estado> listar() {
        var estados = this.estadoRepository.listar();

        if(estados.isEmpty())
            throw new EntidadeNaoEncontradaException("""
                    Não há estados cadastrados no banco de dados.""");

        return estados;
    }

    public Estado buscar(Long id) {
        var estado = this.estadoRepository.buscar(id);

        if(estado == null)
            throw new EntidadeNaoEncontradaException("""
                    Não encontrado estado com código %d.""".formatted(id));

        return estado;
    }
}
