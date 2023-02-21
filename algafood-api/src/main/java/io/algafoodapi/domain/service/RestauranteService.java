package io.algafoodapi.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.algafoodapi.domain.core.Constantes;
import io.algafoodapi.domain.core.validation.ValidacaoException;
import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.RestauranteNaoEncontradoException;
import io.algafoodapi.domain.exception.http409.RestauranteEmUsoException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.repository.CozinhaRepository;
import io.algafoodapi.domain.repository.RestauranteRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;
    private final SmartValidator smartValidator;

    public RestauranteService(final RestauranteRepository restauranteRepository,
                              final CozinhaRepository cozinhaRepository,
                              final SmartValidator smartValidator) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.smartValidator = smartValidator;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Restaurante criar(Restaurante restaurante) {

        var cozinha = this.validarCozinha(restaurante);
        restaurante.setCozinha(cozinha);
        return this.restauranteRepository.save(restaurante);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Restaurante atualizar(final Long idRestaurante, Restaurante restaurante) {

        return this.restauranteRepository.findById(idRestaurante)
                .map(restaurant -> {
                    var cozinha = this.validarCozinha(restaurante);
                    restaurant.setCozinha(cozinha);
                    BeanUtils.copyProperties(restaurante, restaurant, "id", "cozinha",
                            "formasPagamento", "data_hora_utc_cadastro", "produtos", "pedidos"); // TODO - BUG - data_hora_utc_cadastro está ficando nulo na atualização
                    return restaurant;
                })
                .orElseThrow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Restaurante atualizarParcial(Long id, Map<String, Object> dadosOrigem, HttpServletRequest request) {

        var restauranteDoDatabase = this.consultarPorId(id);

        this.merge(dadosOrigem, restauranteDoDatabase, request);

        this.validarValoresAtualizadosDeRestaurante(restauranteDoDatabase, "restaurante");

        return this.atualizar(id, restauranteDoDatabase);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(final Long id) {

        try {
            this.restauranteRepository.deleteById(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new RestauranteNaoEncontradoException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new RestauranteEmUsoException(id); // TODO - BUG - Não captura e não lança
        }
    }

    @Transactional(readOnly = true)
    public Restaurante consultarPorId(final Long id) {

        return this.restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<Restaurante> consultarPorNome(final String nome) {

        var restaurantes = this.restauranteRepository.buscarTodosPorNome(nome)
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(Constantes.NÃO_EXISTEM_RESTAURANTES);
        }

        return restaurantes;
    }

    @Transactional(readOnly = true)
    public List<Restaurante> consultarPorNomeAndTaxas(final String nome, final BigDecimal freteTaxaInicial,
                                                      final BigDecimal freteTaxaFinal) {

        var restaurantes = this.restauranteRepository.findPorCriteria(nome, freteTaxaInicial, freteTaxaFinal)
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(Constantes.NÃO_EXISTEM_RESTAURANTES);
        }

        return restaurantes;
    }

    @Transactional(readOnly = true)
    public List<Restaurante> listar() {

        var restaurantes = this.restauranteRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(Constantes.NÃO_EXISTEM_RESTAURANTES);
        }

        return restaurantes;
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

    private void validarValoresAtualizadosDeRestaurante(Restaurante restaurante, String nomeDoObjeto) {
        var beanPropertyBindingResult = new BeanPropertyBindingResult(restaurante, nomeDoObjeto);

        smartValidator.validate(restaurante, beanPropertyBindingResult);

        if (beanPropertyBindingResult.hasErrors()) { // O BeanPropertyBindingResult armazena os erros, em caso de existirem. Pois isso verificamos se existem.
            throw new ValidacaoException(beanPropertyBindingResult);
        }
    }

    private Cozinha validarCozinha(Restaurante restaurante) {
        var idCozinha = restaurante.getCozinha().getId();
        return this.cozinhaRepository.findById(idCozinha)
            .orElseThrow(() ->
                new RequisicaoMalFormuladaException(String.format(Constantes.COZINHA_NAO_ENCONTRADA, idCozinha)));
    }
}

