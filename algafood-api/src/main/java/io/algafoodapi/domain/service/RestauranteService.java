package io.algafoodapi.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.algafoodapi.domain.exception.http404.CozinhaNaoEncontradaException;
import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.RestauranteNaoEncontradoException;
import io.algafoodapi.domain.exception.http409.RestauranteEmUsoException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.repository.RestauranteRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class RestauranteService {

    public static final String NÃO_EXISTEM_RESTAURANTES = "Não há restaurantes cadastrados.";

    private final RestauranteRepository restauranteRepository;

    private final CozinhaService cozinhaService;

    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaService cozinhaService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaService = cozinhaService;
    }

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

    public Restaurante atualizarParcial(Long id, Map<String, Object> dadosOrigem, HttpServletRequest request) {

        var restauranteDoDatabase = this.consultarPorId(id);

        this.merge(dadosOrigem, restauranteDoDatabase, request);

        return this.atualizar(id, restauranteDoDatabase);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDoDatabase, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true); // Configuração programática: ativa lançar exception quando requisição enviar json com campos marcados com @JsonIgnore. A exceção é lançada no momento da deserialização de json para objeto java.
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // Configuração programática: ativa lançar exception quando requisição enviar json com campos para mais (campos que não existem). A exceção é lançada no momento da deserialização de json para objeto java.
            Restaurante restauranteAtual = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field campo = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                campo.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(campo, restauranteAtual);
                ReflectionUtils.setField(campo, restauranteDoDatabase, novoValor);
            });
        } catch (IllegalArgumentException illegalArgumentException) {
            Throwable causaRaiz = ExceptionUtils.getRootCause(illegalArgumentException);
            throw new HttpMessageNotReadableException(illegalArgumentException.getMessage(), causaRaiz, serverHttpRequest);
        }
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

        if (true) {
            throw new IllegalArgumentException("Teste");
        }

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

