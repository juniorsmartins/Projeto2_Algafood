package io.algafoodapi.util;

public class CriadorDeJsons {

    public static String jsonDeCozinha(String nome) {
        return """
                { "gastronomia" : "%s" }
                """.formatted(nome);
    }

    public static String jsonDeFormaPagamento(String descricao) {
        return """
                { "descricao" : "%s" }
                """.formatted(descricao.toUpperCase());
    }
}

