package io.algafoodapi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static byte[] converterDtoRequestParaJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}

