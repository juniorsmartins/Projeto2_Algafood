package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
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

    public static final String NÃO_ENCONTRADO_ESTADO_COM_ID = "Não encontrado estado com código %d.";
    public static final String PROIBIDO_APAGAR_ESTADO_EM_USO_COM_ID = "Proibido apagar estado em uso. ID: %d.";
    public static final String NAO_EXISTEM_ESTADOS_CADASTRADOS = "Não há estados cadastrados no banco de dados.";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return this.estadoRepository.saveAndFlush(estado);
    }

    public Estado atualizar(Long id, Estado estadoAtual) {

        var estado = this.consultarPorId(id);
        BeanUtils.copyProperties(estadoAtual, estado, "id");

        return this.salvar(estado);
    }

    public void excluirPorId(Long id) {

        try {
            this.estadoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EstadoNaoEncontradoException(String.format(EstadoService.NÃO_ENCONTRADO_ESTADO_COM_ID, id));

        } catch (DataIntegrityViolationException violationException) {
            throw new RequisicaoMalFormuladaException(String.format(PROIBIDO_APAGAR_ESTADO_EM_USO_COM_ID, id));
        }
    }

    public Estado consultarPorId(Long id) {

        return this.estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(
                        String.format(NÃO_ENCONTRADO_ESTADO_COM_ID, id)));
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

