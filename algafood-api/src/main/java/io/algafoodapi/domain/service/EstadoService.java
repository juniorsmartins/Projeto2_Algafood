package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.http404.EstadoNaoEncontradoException;
import io.algafoodapi.domain.exception.http409.EstadoEmUsoException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EstadoService {

    public static final String NAO_EXISTEM_ESTADOS_CADASTRADOS = "Não há estados cadastrados no banco de dados.";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado criar(Estado estado) {
        return this.estadoRepository.saveAndFlush(estado);
    }

    public Estado atualizar(Long id, Estado estadoAtual) {

        var estado = this.consultarPorId(id);
        BeanUtils.copyProperties(estadoAtual, estado, "id");

        return this.criar(estado);
    }

    public void excluirPorId(Long id) {

        try {
            this.estadoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EstadoNaoEncontradoException(id);

        } catch (DataIntegrityViolationException violationException) {
            throw new EstadoEmUsoException(id);
        }
    }

    public Estado consultarPorId(Long id) {

        return this.estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    public List<Estado> listar() {

        var estados = this.estadoRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Estado::getId).reversed())
                .toList();

        if(estados.isEmpty())
            throw new EstadoNaoEncontradoException(String.format(NAO_EXISTEM_ESTADOS_CADASTRADOS));

        return estados;
    }
}

