package io.algafoodapi.business.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.algafoodapi.business.core.Constantes;
import io.algafoodapi.business.core.validation.ValidacaoException;
import io.algafoodapi.business.exception.http400.RequisicaoMalFormuladaException;
import io.algafoodapi.business.exception.http404.ProdutoNaoEncontradoException;
import io.algafoodapi.business.exception.http404.RestauranteNaoEncontradoException;
import io.algafoodapi.business.exception.http409.RestauranteEmUsoException;
import io.algafoodapi.business.model.FormaPagamento;
import io.algafoodapi.business.model.Produto;
import io.algafoodapi.business.model.Restaurante;
import io.algafoodapi.business.ports.CozinhaRepository;
import io.algafoodapi.business.utils.ServiceUtils;
import io.algafoodapi.infraestrutura.repository.PoliticaCrudBaseRepository;
import io.algafoodapi.infraestrutura.repository.PoliticaRestauranteRepository;
import io.algafoodapi.infraestrutura.repository.jpa.CidadeRepositoryJpa;
import io.algafoodapi.infraestrutura.repository.jpa.ProdutoRepositoryJpa;
import io.algafoodapi.presentation.mapper.RestauranteMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class RestauranteServiceImpl implements PoliticaCrudBaseService<Restaurante, Long>, PoliticaRestauranteService<Restaurante, Long> {

    @Autowired
    private PoliticaCrudBaseRepository<Restaurante, Long> crudRepository;

    @Autowired
    private PoliticaRestauranteRepository<Long> restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CidadeRepositoryJpa cidadeRepositoryJpa;

    @Autowired
    private ProdutoRepositoryJpa produtoRepositoryJpa;

    @Autowired
    private SmartValidator smartValidator;

    @Autowired
    private RestauranteMapper restauranteMapper;

    @Autowired
    private ServiceUtils serviceUtils;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Restaurante cadastrar(Restaurante restaurante) {

        return Optional.of(restaurante)
            .map(this::validarCozinha)
            .map(this::validarEndereco)
            .map(this.crudRepository::salvar)
            .orElseThrow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public Restaurante atualizar(Restaurante entidade) {
        var idRestaurante = entidade.getId();

        return this.crudRepository.consultarPorId(idRestaurante)
            .map(restaurant -> {
                this.validarCozinha(entidade);
                this.restauranteMapper.copiarValoresDaOrigemParaDestino(entidade, restaurant);
                return restaurant;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Restaurante> pesquisar(final Restaurante entidade, final Pageable paginacao) {

        var condicoesDePesquisa = this.serviceUtils.criarCondicoesDePesquisa(entidade);
        return this.crudRepository.pesquisar(condicoesDePesquisa, paginacao);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void deletar(final Long id) {

        this.crudRepository.consultarPorId(id)
            .map(restaurant -> {
                try {
                    this.crudRepository.deletar(restaurant);
                } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                    throw new RestauranteEmUsoException(id);
                }
                return true;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
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

    @Transactional(readOnly = true)
    @Override
    public Restaurante consultarPorId(final Long id) {

        return this.restauranteRepository.findById(id)
            .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    @Override
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

//    @Transactional(readOnly = true)
//    @Override
//    public List<Restaurante> consultarPorNomeAndTaxas(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal) {
//
//        var restaurantes = this.restauranteRepository.findPorCriteria(nome, freteTaxaInicial, freteTaxaFinal)
//            .stream()
//            .sorted(Comparator.comparing(Restaurante::getId).reversed())
//            .toList();
//
//        if (restaurantes.isEmpty()) {
//            throw new RestauranteNaoEncontradoException(Constantes.NÃO_EXISTEM_RESTAURANTES);
//        }
//
//        return restaurantes;
//    }

    @Transactional(readOnly = true)
    @Override
    public List<Restaurante> listar() {

        var restaurantes = this.crudRepository.listar()
            .stream()
            .sorted(Comparator.comparing(Restaurante::getId).reversed())
            .toList();

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(Constantes.NÃO_EXISTEM_RESTAURANTES);
        }

        return restaurantes;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void ativar(final Long idRestaurante) {
        this.restauranteRepository.findById(idRestaurante)
            .map(restaurante -> {
                restaurante.ativar();
                return restaurante;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void inativar(final Long idRestaurante) {
        this.restauranteRepository.findById(idRestaurante)
            .map(restaurante -> {
                restaurante.inativar();
                return restaurante;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(idRestaurante));
    }

    @Transactional(readOnly = true)
    @Override
    public List<FormaPagamento> consultarFormasDePagamentoPorRestaurante(final Long id) {

        return this.crudRepository.consultarPorId(id)
            .map(Restaurante::getFormasPagamento)
            .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Override
    public Produto cadastrarProdutoPorRestaurante(final Long id, final Produto produto) {

        return this.restauranteRepository.findById(id)
            .map(restaurante -> {
                this.produtoRepositoryJpa.save(produto);
                restaurante.getProdutos().add(produto);
                return produto;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Produto> consultarProdutosPorRestaurante(final Long id) {

        return this.restauranteRepository.findById(id)
            .map(restaurante -> {
                var produtos = restaurante.getProdutos();
                if (produtos.isEmpty()) {
                    throw new ProdutoNaoEncontradoException();
                }
                return produtos;
            })
            .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
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

