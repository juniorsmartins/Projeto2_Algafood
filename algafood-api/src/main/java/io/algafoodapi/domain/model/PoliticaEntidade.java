package io.algafoodapi.domain.model;

public interface PoliticaEntidade<I> {

    I getId();

    void setId(I id);

    String getNome();

    void setNome(String nome);
}

