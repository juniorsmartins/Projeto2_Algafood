package io.algafoodapi.camada2_business.model;

public interface PoliticaEntidade<I> {

    I getId();

    void setId(I id);

    String getNome();

    void setNome(String nome);
}

