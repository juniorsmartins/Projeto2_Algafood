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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class EstadoService {

    public static final String NAO_EXISTEM_ESTADOS_CADASTRADOS = "Não há estados cadastrados no banco de dados.";

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Estado criar(Estado estado) {
        return this.estadoRepository.saveAndFlush(estado);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Estado atualizar(Long id, Estado estadoAtual) {

        var estado = this.consultarPorId(id);
        BeanUtils.copyProperties(estadoAtual, estado, "id");

        return this.criar(estado);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(Long id) {

        try {
            this.estadoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EstadoNaoEncontradoException(id);

        } catch (DataIntegrityViolationException violationException) {
            throw new EstadoEmUsoException(id);
        }
    }

    @Transactional(readOnly = true)
    public Estado consultarPorId(Long id) {

        return this.estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
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

