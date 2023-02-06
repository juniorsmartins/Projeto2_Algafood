package io.algafoodapi.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.algafoodapi.domain.exception.EntidadeEmUsoException;
import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Cozinha;
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

    public static final String NÃO_ENCONTRADO_RESTAURANTE_COM_ID = "Não encontrado restaurante com código %d.";
    public static final String PROIBIDO_APAGAR_RESTAURANTE_EM_USO_COM_ID = "Proibido apagar restaurante em uso. ID: %d.";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    public Restaurante criar(Restaurante restaurante) {

        try {
            var cozinha = this.cozinhaService.consultarPorId(restaurante.getCozinha().getId());
            restaurante.setCozinha(cozinha);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage());
        }

        return this.restauranteRepository.saveAndFlush(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante restauranteAtual) {

        var restaurante = this.consultarPorId(id);

        try {
            var cozinha = this.cozinhaService.consultarPorId(restauranteAtual.getCozinha().getId());
            restauranteAtual.setCozinha(cozinha);

        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage());
        }

        BeanUtils.copyProperties(restauranteAtual, restaurante, "id",
                "formasPagamento", "endereco", "dataCadastro", "produtos");

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
            throw new EntidadeNaoEncontradaException(String.format(NÃO_ENCONTRADO_RESTAURANTE_COM_ID, id));

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new RequisicaoMalFormuladaException(String.format(PROIBIDO_APAGAR_RESTAURANTE_EM_USO_COM_ID, id));
        }
    }

    public Restaurante consultarPorId(Long id) {

        return this.restauranteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(NÃO_ENCONTRADO_RESTAURANTE_COM_ID, id)));
    }

    public List<Restaurante> consultarPorNome(String nome) {

        var restaurantes = this.restauranteRepository.buscarTodosPorNome(nome)
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

    public List<Restaurante> listar() {

        var restaurantes = this.restauranteRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Restaurante::getId).reversed())
                .toList();

        if(restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há restaurantes cadastrados no banco de dados."));

        return restaurantes;
    }
}

