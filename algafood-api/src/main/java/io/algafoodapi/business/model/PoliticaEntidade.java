package io.algafoodapi.business.model;

public interface PoliticaEntidade<I> {

    I getId();

    void setId(I id);

    String getNome();

    void setNome(String nome);
}

