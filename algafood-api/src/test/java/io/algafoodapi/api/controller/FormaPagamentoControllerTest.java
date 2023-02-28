package io.algafoodapi.api.controller;

import io.algafoodapi.util.CriadorDeBuilders;
import io.algafoodapi.util.CriadorDeJsons;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FormaPagamentoControllerTest {

    private static final String ENDPOINT = "/v1/formas-pagamento";
    private static final String UTF8 = "UTF-8";

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void criarCenario() { }

    @AfterEach
    void destruirCenario() { }

    @Test
    @Order(1)
    @DisplayName("Salvar - Fluxo Principal I - 201")
    void deveRetornarHttp201_quandoCadastrar() throws Exception {
        var dtoRequest = CriadorDeBuilders.geraFormaPagamentoBuilder().build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("Salvar - Fluxo Principal II - descrição igual")
    void deveRetornarDescricaoIgual_quandoCadastrar() throws Exception {
        var dtoRequest = CriadorDeBuilders.geraFormaPagamentoBuilder().build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.descricao", Matchers.is(dtoRequest.getDescricao())));
    }


}

