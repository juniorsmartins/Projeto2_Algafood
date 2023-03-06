package io.algafoodapi.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.algafoodapi.api.mapper.RestauranteMapper;
import io.algafoodapi.domain.core.Constantes;
import io.algafoodapi.domain.core.validation.ValidacaoException;
import io.algafoodapi.domain.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.exception.http404.RestauranteNaoEncontradoException;
import io.algafoodapi.domain.exception.http409.RestauranteEmUsoException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.infraestrutura.repository.jpa.CidadeRepositoryJpa;
import io.algafoodapi.domain.ports.CozinhaRepository;
import io.algafoodapi.domain.ports.RestauranteRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import java.util.Optional;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    private final CozinhaRepository cozinhaRepository;

    private final CidadeRepositoryJpa cidadeRepositoryJpa;

    private final SmartValidator smartValidator;

    private final RestauranteMapper restauranteMapper;

    public RestauranteService(final RestauranteRepository restauranteRepository,
                              final CozinhaRepository cozinhaRepository,
                              final CidadeRepositoryJpa cidadeRepositoryJpa,
                              final SmartValidator smartValidator,
                              final RestauranteMapper restauranteMapper) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.cidadeRepositoryJpa = cidadeRepositoryJpa;
        this.smartValidator = smartValidator;
        this.restauranteMapper = restauranteMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Restaurante criar(Restaurante restaurante) {

        return Optional.of(restaurante)
            .map(this::validarCozinha)
            .map(this::validarEndereco)
            .map(this.restauranteRepository::save)
            .orElseThrow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Restaurante atualizar(final Long idRestaurante, Restaurante restauranteNovasInfos) {

        return this.restauranteRepository.findById(idRestaurante)
            .map(restaurant -> {
                this.validarCozinha(restauranteNovasInfos);
                this.restauranteMapper.copiarValoresDaOrigemParaDestino(restauranteNovasInfos, restaurant);
                return restaurant;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void excluirPorId(final Long id) {

        try {
            this.restauranteRepository.deleteById(id);
//            this.restauranteRepository.flush();

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new RestauranteNaoEncontradoException(id);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new RestauranteEmUsoException(id);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void ativar(final Long idRestaurante) {
        this.restauranteRepository.findById(idRestaurante)
            .map(restaurante -> {
                restaurante.ativar();
                return restaurante;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void inativar(final Long idRestaurante) {
        this.restauranteRepository.findById(idRestaurante)
            .map(restaurante -> {
                restaurante.inativar();
                return restaurante;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Restaurante atualizarParcial(final Long idRestaurante, Map<String, Object> dadosOrigem, final HttpServletRequest request) {

        // TODO - verificar em caso de atualização para cozinha inexistente

        return this.restauranteRepository.findById(idRestaurante)
                .map(restaurante -> {
                    this.merge(dadosOrigem, restaurante, request);
                    this.validarValoresAtualizadosDeRestaurante(restaurante, "restaurante");
                    return restaurante;
                })
                .orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restaurante, HttpServletRequest request) {
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
                ReflectionUtils.setField(campo, restaurante, novoValor);
            });
        } catch (IllegalArgumentException illegalArgumentException) {
            Throwable causaRaiz = ExceptionUtils.getRootCause(illegalArgumentException);
            throw new HttpMessageNotReadableException(illegalArgumentException.getMessage(), causaRaiz, serverHttpRequest);
        }
    }

    private void validarValoresAtualizadosDeRestaurante(Restaurante restaurante, String nomeDoObjeto) {

        var dtoRequest = this.restauranteMapper.converterEntidadeParaDtoRequest(restaurante);
        var beanPropertyBindingResult = new BeanPropertyBindingResult(dtoRequest, nomeDoObjeto);
        smartValidator.validate(dtoRequest, beanPropertyBindingResult);

        if (beanPropertyBindingResult.hasErrors()) { // O BeanPropertyBindingResult armazena os erros, em caso de existirem, pois, verificamos se existem.
            throw new ValidacaoException(beanPropertyBindingResult);
        }
    }

    private Restaurante validarCozinha(Restaurante restaurante) {
        var idCozinha = restaurante.getCozinha().getId();
        var cozinha = this.cozinhaRepository.findById(idCozinha)
            .orElseThrow(() ->
                new RequisicaoMalFormuladaException(String.format(Constantes.COZINHA_NAO_ENCONTRADA, idCozinha)));
        restaurante.setCozinha(cozinha);
        return restaurante;
    }

    private Restaurante validarEndereco(Restaurante restaurante) {
        var idCidade = restaurante.getEndereco().getCidade().getId();
        var cidade = this.cidadeRepositoryJpa.findById(idCidade)
            .orElseThrow(() ->
                    new RequisicaoMalFormuladaException(String.format(Constantes.CIDADE_NAO_ENCONTRADA, idCidade)));
        restaurante.getEndereco().setCidade(cidade);
        return restaurante;
    }
}

