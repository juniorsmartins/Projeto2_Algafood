package io.algafoodapi.domain.exception;

import lombok.Getter;

@Getter
public enum TipoDeErroEnum {

    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada.", "/entidade-nao-encontrada");

    private String titulo;
    private String caminho;

    TipoDeErroEnum(String titulo, String caminho) {
        this.titulo = titulo;
        this.caminho = "https://algafood.com.br" + caminho;
    }
}
