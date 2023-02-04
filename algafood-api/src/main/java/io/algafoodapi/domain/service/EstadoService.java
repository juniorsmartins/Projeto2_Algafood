package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class EstadoService {

    public static final String NÃO_ENCONTRADO_ESTADO_COM_ID = "Não encontrado estado com código %d.";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return this.estadoRepository.saveAndFlush(estado);
    }

    public Estado atualizar(Long id, Estado estadoAtual) throws EntidadeNaoEncontradaException {

        var estado = this.consultarPorId(id);
        BeanUtils.copyProperties(estadoAtual, estado, "id");
        return this.salvar(estado);
    }

    public void excluirPorId(Long id) {

        try {
            this.estadoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrado estado com código %d.", id));

        } catch (DataIntegrityViolationException violationException) {
            throw new EntidadeEmUsoException(String.format("Não pode ser removido estado com código %d, pois está em uso.", id));
        }
    }

    public Estado consultarPorId(Long id) {

        return this.estadoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(NÃO_ENCONTRADO_ESTADO_COM_ID, id)));
    }

    public List<Estado> buscarTodos() {
        var estados = this.estadoRepository.findAll();

        if(estados.isEmpty())
            throw new EntidadeNaoEncontradaException("""
                    Não há estados cadastrados no banco de dados.""");

        return estados;
    }
}
