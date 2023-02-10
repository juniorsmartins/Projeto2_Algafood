package io.algafoodapi.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.algafoodapi.domain.exception.http404.CozinhaNaoEncontradaException;
import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.RestauranteNaoEncontradoException;
import io.algafoodapi.domain.exception.http409.RestauranteEmUsoException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class RestauranteService {

    public static final String NÃO_EXISTEM_RESTAURANTES = "Não há restaurantes cadastrados.";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    public Restaurante criar(Restaurante restaurante) {

        try {
            var cozinha = this.cozinhaService.consultarPorId(restaurante.getCozinha().getId());
            restaurante.setCozinha(cozinha);

        } catch (CozinhaNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage(), naoEncontradaException);
        }

        return this.restauranteRepository.saveAndFlush(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante restauranteAtual) {

        var restaurante = this.consultarPorId(id);

        try {
            var cozinha = this.cozinhaService.consultarPorId(restauranteAtual.getCozinha().getId());
            restauranteAtual.setCozinha(cozinha);

        } catch (CozinhaNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage(), naoEncontradaException);
        }

        BeanUtils.copyProperties(restauranteAtual, restaurante, "id",
                "formasPagamento", "endereco", "dataCadastro", "produtos");

        return this.restauranteRepository.saveAndFlush(restaurante);
    }

    public Restaurante atualizarParcial(Long id, Map<String, Object> dadosOrigem) {

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
            throw new RestauranteNaoEncontradoException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new RestauranteEmUsoException(id);
        }
    }

    public Restaurante consultarPorId(Long id) {

        return this.restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    public List<Restaurante> consultarPorNome(String nome) {

        var restaurantes = this.restauranteRepository.buscarTodosPorNome(nome)
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if(restaurantes.isEmpty())
            throw new RestauranteNaoEncontradoException(NÃO_EXISTEM_RESTAURANTES);

        return restaurantes;
    }

    public List<Restaurante> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal) {

        var restaurantes = this.restauranteRepository.findPorCriteria(nome, freteTaxaInicial, freteTaxaFinal)
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if(restaurantes.isEmpty())
            throw new RestauranteNaoEncontradoException(NÃO_EXISTEM_RESTAURANTES);

        return restaurantes;
    }

    public List<Restaurante> listar() {

        var restaurantes = this.restauranteRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if(restaurantes.isEmpty())
            throw new RestauranteNaoEncontradoException(NÃO_EXISTEM_RESTAURANTES);

        return restaurantes;
    }
}

