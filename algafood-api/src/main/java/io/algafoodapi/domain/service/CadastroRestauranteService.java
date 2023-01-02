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
import java.util.List;
import java.util.Map;

@Service
public final class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    public Restaurante salvar(Restaurante restaurante) throws EntidadeNaoEncontradaException {

        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = this.cozinhaService.buscar(cozinhaId);
        restaurante.setCozinha(cozinha);

        return this.restauranteRepository.salvar(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante restauranteAtual) throws EntidadeNaoEncontradaException, RequisicaoMalFormuladaException {

        var restaurante = this.buscar(id);

        Cozinha cozinha;
        try {
            cozinha = this.cozinhaService.buscar(restauranteAtual.getCozinha().getId());
        } catch (EntidadeNaoEncontradaException naoEncontradaException) {
            throw new RequisicaoMalFormuladaException(naoEncontradaException.getMessage());
        }
        restauranteAtual.setCozinha(cozinha);

        BeanUtils.copyProperties(restauranteAtual, restaurante, "id");

        return this.restauranteRepository.salvar(restaurante);
    }

    public Restaurante atualizarParcial(Long id, Map<String, Object> dadosOrigem) throws EntidadeNaoEncontradaException, RequisicaoMalFormuladaException {

        var restauranteDoDatabase = this.buscar(id);

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

    public void excluir(Long id) {

        try {
            this.restauranteRepository.remover(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrado restaurante com código %d.", id));
        }
    }

    public Restaurante buscar(Long id) {

        var restaurante = this.restauranteRepository.buscar(id);

        if(restaurante == null)
            throw new EntidadeNaoEncontradaException("""
                    Não encontrado restaurante com código %d.""".formatted(id));

        return restaurante;
    }

    public List<Restaurante> listar() {

        var restaurantes = this.restauranteRepository.listar();

        if(restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há restaurantes cadastrados no banco de dados."));

        return restaurantes;
    }
}
