package io.algafoodapi.util;

import com.github.javafaker.Faker;
import io.algafoodapi.presentation.dto.request.FormaPagamentoDtoRequest;
import io.algafoodapi.business.model.FormaPagamento;

public final class CriadorDeBuilders {

    private static Faker faker = new Faker();

    public static FormaPagamento.FormaPagamentoBuilder gerarFormaPagamentoBuilder() {
        return FormaPagamento.builder()
            .nome(faker.lorem().characters(10, 250));
    }

    public static FormaPagamentoDtoRequest.FormaPagamentoDtoRequestBuilder gerarFormaPagamentoDtoRequestBuilder() {
        return FormaPagamentoDtoRequest.builder()
            .nome(faker.lorem().characters(1, 250));
    }

}

