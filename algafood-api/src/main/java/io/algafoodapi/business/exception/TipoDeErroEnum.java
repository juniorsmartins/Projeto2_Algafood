package io.algafoodapi.business.exception;

import lombok.Getter;

@Getter
public enum TipoDeErroEnum {

    RECURSO_NAO_ENCONTRADO("Recurso não encontrado.", "/recurso-nao-encontrado"),

    RECURSO_EM_USO("Recurso em uso.", "/recurso-em-uso"),

    REQUISICAO_MAL_FORMULADA("Requisição mal formulada.", "/requisicao-mal-formulada"),

    ERRO_DE_SISTEMA("Erro de sistema.", "/erro-de-sistema"),

    DADOS_INVALIDOS("Dados inválidos.", "/dados-invalidos"),

    REGRA_VIOLADA("Regra de negócio violada.", "/regra-violada");

    private String titulo;
    private String caminho;

    TipoDeErroEnum(String titulo, String caminho) {
        this.titulo = titulo;
        this.caminho = "https://algafood.com.br" + caminho;
    }
}

