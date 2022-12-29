package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.exception.RequisicaoMalFormuladaException;
import io.algafoodapi.domain.model.Cozinha;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.repository.CozinhaRepository;
import io.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

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

    public void excluir(Long id) {

        try {
            this.restauranteRepository.remover(id);

        } catch (EmptyResultDataAccessException dataAccessException) {
            throw new EntidadeNaoEncontradaException(String.format("Não encontrado restaurante com código %d.", id));
        }
    }

    public List<Restaurante> listar() {
        var restaurantes = this.restauranteRepository.listar();

        if(restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format("Não há restaurantes cadastrados no banco de dados."));

        return restaurantes;
    }

    public Restaurante buscar(Long id) {

        var restaurante = this.restauranteRepository.buscar(id);

        if(restaurante == null)
            throw new EntidadeNaoEncontradaException("""
                    Não encontrado restaurante com código %d.""".formatted(id));

        return restaurante;
    }
}
