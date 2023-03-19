package io.algafoodapi.business.service;

import io.algafoodapi.business.exception.http404.PratoNaoEncontradoException;
import io.algafoodapi.business.model.Prato;
import io.algafoodapi.business.ports.PratoRepository;
import io.algafoodapi.infraestrutura.repository.spec.PratoFactorySpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    @Autowired
    private PratoRepository pratoRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Prato create(Prato prato) {
        return this.pratoRepository.saveAndFlush(prato);
    }

    @Transactional(readOnly = true)
    public List<Prato> pratosGratisComNomeSemelhante(String nome) {

        var pratos = this.pratoRepository.findAll(PratoFactorySpecs.comValorGratis()
                .and(PratoFactorySpecs.comNomeSemelhante(nome)));

        if(pratos.isEmpty())
            throw new PratoNaoEncontradoException("Não encontrados pratos com tais especificações.");

        return pratos;
    }

    @Transactional(readOnly = true)
    public Optional<Prato> pratoPrimeiro() {

        try {
            return this.pratoRepository.buscarPrimeiro();

        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new PratoNaoEncontradoException("Não existem pratos cadastrados!");
        }
    }
}

