package io.algafoodapi.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public final class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    public Restaurante salvar(Restaurante restaurante) throws EntidadeNaoEncontradaException {

        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = this.cozinhaService.consultarPorId(cozinhaId);
        restaurante.setCozinha(cozinha);

        return this.restauranteRepository.saveAndFlush(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante restauranteAtual) throws EntidadeNaoEncontradaException, RequisicaoMalFormuladaException {

        var restaurante = this.consultarPorId(id);

        Cozinha cozinha;
        try {
            cozinha = this.cozinhaService.consultarPorId(restauranteAtual.getCozinha().getId());
        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage());
        }
        restauranteAtual.setCozinha(cozinha);

        BeanUtils.copyProperties(restauranteAtual, restaurante, "id", "formasPagamento", "endereco");

        return this.restauranteRepository.saveAndFlush(restaurante);
    }

    public Restaurante atualizarParcial(Long id, Map<String, Object> dadosOrigem) throws EntidadeNaoEncontradaException, RequisicaoMalFormuladaException {

        var restauranteDoDatabase = this.consultarPorId(id);

        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteAtual = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field campo = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            campo.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(campo, restauranteAtual);
            ReflectionUtils.setField(campo, restauranteDoDatabase, novoValor);
        });

        return this.atualizar(id, restauranteDoDatabase);
    }

    public void excluirPorId(Long id) {

        try {
            this.restauranteRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrado restaurante com código %d.", id));
        }
    }

    public Restaurante consultarPorId(Long id) {

        return this.restauranteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("""
                    Não encontrado restaurante com código %d.""".formatted(id)));
    }

    public List<Restaurante> buscarTodosPorNome(String nome) {

        var restaurantes = this.restauranteRepository.buscarTodosPorNome(nome)
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if(restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há restaurantes cadastrados no banco de dados."));

        return restaurantes;
    }

    public List<Restaurante> buscarTodos() {

        var restaurantes = this.restauranteRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if(restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há restaurantes cadastrados no banco de dados."));

        return restaurantes;
    }

    public List<Restaurante> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal) {

        var restaurantes = this.restauranteRepository.findPorCriteria(nome, freteTaxaInicial, freteTaxaFinal)
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if(restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há restaurantes cadastrados no banco de dados."));

        return restaurantes;
    }
}
