package io.algafoodapi.domain.exception;

import lombok.Getter;

@Getter
public enum TipoDeErroEnum {

    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada.", "/entidade-nao-encontrada"),
    ENTIDADE_EM_USO("Entidade em uso.", "/entidade-em-uso"),
    REQUISICAO_MAL_FORMULADA("Requisição mal formulada.", "/requisicao-mal-formulada"),
    ERRO_DE_SISTEMA("Erro de sistema.", "/erro-de-sistema");

    private String titulo;
    private String caminho;

    TipoDeErroEnum(String titulo, String caminho) {
        this.titulo = titulo;
        this.caminho = "https://algafood.com.br" + caminho;
    }
}

