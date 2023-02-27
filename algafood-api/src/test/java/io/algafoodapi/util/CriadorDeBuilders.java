package io.algafoodapi.util;

import com.github.javafaker.Faker;
import io.algafoodapi.domain.model.FormaPagamento;

public final class CriadorDeBuilders {

    private static Faker faker = new Faker();

    public static FormaPagamento.FormaPagamentoBuilder geraFormaPagamentoBuilder() {
        return FormaPagamento.builder()
                .descricao(faker.lorem().characters(5, 250));
    }


}

