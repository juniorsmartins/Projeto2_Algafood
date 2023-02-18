package io.algafoodapi.util;

public class CriadorDeJsons {

    public static String jsonDeCozinha(String nome) {
        return """
                { "gastronomia" : "%s" }
                """.formatted(nome);
    }
}
