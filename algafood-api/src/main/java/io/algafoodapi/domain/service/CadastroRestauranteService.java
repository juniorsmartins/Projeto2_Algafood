package io.algafoodapi.domain.service;

import io.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import io.algafoodapi.domain.model.Restaurante;
import io.algafoodapi.domain.repository.CozinhaRepository;
import io.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = this.cozinhaRepository.buscar(cozinhaId);
        if(cozinha == null)
            throw new EntidadeNaoEncontradaException("""
                    Não existe cadastro de cozinha com código %d.""".formatted(cozinhaId));
        restaurante.setCozinha(cozinha);

        return this.restauranteRepository.salvar(restaurante);
    }

    public List<Restaurante> listar() {
        var restaurantes = this.restauranteRepository.listar();

        if(restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException("Não há restaurantes cadastrados no banco de dados.");

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
