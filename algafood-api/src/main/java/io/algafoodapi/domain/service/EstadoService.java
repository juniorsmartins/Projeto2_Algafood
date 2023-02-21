package io.algafoodapi.domain.service;

import io.algafoodapi.domain.core.Constantes;
import io.algafoodapi.domain.exception.http404.EstadoNaoEncontradoException;
import io.algafoodapi.domain.exception.http409.EstadoEmUsoException;
import io.algafoodapi.domain.model.Estado;
import io.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
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

    private final EstadoRepository estadoRepository;

    public EstadoService(final EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Estado criar(final Estado estado) {
        return this.estadoRepository.save(estado);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Estado atualizar(final Long idEstado, final Estado estado) {

        return this.estadoRepository.findById(idEstado)
                .map(state -> {
                    BeanUtils.copyProperties(estado, state, "id");
                    return state;
                })
                .orElseThrow(() -> new EstadoNaoEncontradoException(idEstado));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(final Long idEstado) {

        try {
            this.estadoRepository.deleteById(idEstado);
            this.estadoRepository.flush(); // Manda descarregar as operações antes do commit da transação

        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new EstadoNaoEncontradoException(idEstado);

        } catch (DataIntegrityViolationException violationException) {
            throw new EstadoEmUsoException(idEstado);
        }
    }

    @Transactional(readOnly = true)
    public Estado consultarPorId(final Long idEstado) {

        return this.estadoRepository.findById(idEstado)
                .orElseThrow(() -> new EstadoNaoEncontradoException(idEstado));
    }

    @Transactional(readOnly = true)
    public List<Estado> listar() {

        var estados = this.estadoRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Estado::getId).reversed())
                .toList();

        if (estados.isEmpty()) {
            throw new EstadoNaoEncontradoException(Constantes.NAO_EXISTEM_ESTADOS_CADASTRADOS);
        }

        return estados;
    }
}

