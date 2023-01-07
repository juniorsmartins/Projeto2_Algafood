package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Prato;
import io.algafoodapi.domain.repository.PratoRepository;
import io.algafoodapi.infraestrutura.repository.spec.PratoFactorySpecs;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    @Autowired
    private PratoRepository pratoRepository;

    public Prato create(Prato prato) {
        return this.pratoRepository.saveAndFlush(prato);
    }

    public List<Prato> pratosGratisComNomeSemelhante(String nome) {

        var pratos = this.pratoRepository.findAll(PratoFactorySpecs.comValorGratis()
                .and(PratoFactorySpecs.comNomeSemelhante(nome)));

        if(pratos.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não encontrados pratos com tais especificações."));

        return pratos;
    }

    public Optional<Prato> pratoPrimeiro() {

        try {
            return this.pratoRepository.buscarPrimeiro();

        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não existem pratos cadastrados!"));
        }
    }
}

