package io.algafoodapi.api.controller;

import io.algafoodapi.infraestrutura.repository.jpa.FormaPagamentoRepositoryJpa;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FormaPagamentoControllerIntegracaoTest {

    private static final String ENDPOINT = "/v1/formas-pagamento";
    private static final String UTF8 = "UTF-8";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FormaPagamentoRepositoryJpa formaPagamentoRepositoryJpa;

    @BeforeEach
    void criarCenario() { }

    @AfterEach
    void destruirCenario() { }

    @Test
    @Order(1)
    @DisplayName("Cadastrar - Fluxo Principal I - 201")
    void deveRetornarHttp201_quandoCadastrar() throws Exception {
        var dtoRequest = CriadorDeBuilders.gerarFormaPagamentoDtoRequestBuilder()
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }

    @Test
    @Order(2)
    @DisplayName("Cadastrar - Fluxo Principal II - descrição padronizada")
    void deveRetornarDescricaoPadronizada_quandoCadastrar() throws Exception {
        var dtoRequest = CriadorDeBuilders.gerarFormaPagamentoDtoRequestBuilder()
            .build();

        var descricaoPadronizada = dtoRequest.getDescricao().toUpperCase();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.descricao",
                    Matchers.equalTo(descricaoPadronizada)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(3)
    @DisplayName("Cadastrar - Fluxo de Exceção I - sem descricao")
    void deveRetornarHttp400_quandoCadastrarSemDescricao() throws Exception {
        var dtoRequest = CriadorDeBuilders.gerarFormaPagamentoDtoRequestBuilder()
            .descricao(null)
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT.concat("/"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(5)
    @DisplayName("Atualizar - Fluxo Principal I - 200")
    void deveRetornarHttp200_quandoAtualizar() throws Exception {
        var formaPagamentoSalva = CriadorDeBuilders.gerarFormaPagamentoBuilder()
            .build();
        formaPagamentoSalva = this.formaPagamentoRepositoryJpa.save(formaPagamentoSalva);

        var dtoRequest = CriadorDeBuilders.gerarFormaPagamentoDtoRequestBuilder()
            .build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT.concat("/") + formaPagamentoSalva.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(6)
    @DisplayName("Atualizar - Fluxo Principal II - descrição padronizada")
    void deveRetornarDescricaoPadronizada_quandoAtualizar() throws Exception {
        var formaPagamentoSalva = CriadorDeBuilders.gerarFormaPagamentoBuilder()
            .build();
        formaPagamentoSalva = this.formaPagamentoRepositoryJpa.save(formaPagamentoSalva);

        var dtoRequest = CriadorDeBuilders.gerarFormaPagamentoDtoRequestBuilder()
            .build();

        var descricaoPadronizada = dtoRequest.getDescricao().toUpperCase();

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT.concat("/") + formaPagamentoSalva.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.descricao", Matchers.equalTo(descricaoPadronizada)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(7)
    @DisplayName("Atualizar - Fluxo de Exceção I - id inexistente")
    void deveRetornarHttp404_quandoAtualizarSemId() throws Exception {
        var idInexistente = Math.round((Math.random() + 1) * 10000);
        var dtoRequest = CriadorDeBuilders.gerarFormaPagamentoDtoRequestBuilder()
            .build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT.concat("/") + idInexistente)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF8)
                .content(CriadorDeJsons.converterDtoRequestParaJson(dtoRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(10)
    @DisplayName("Listar - Fluxo Principal I - http 200")
    void deveRetornarHttp200_quandoListar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(11)
    @DisplayName("Listar - Fluxo principal II - dois itens")
    void deveRetornarDoisItens_quandoListar() throws Exception {
        var formaPagamentoUm = CriadorDeBuilders.gerarFormaPagamentoBuilder()
            .build();
        var formaPagamentoDois = CriadorDeBuilders.gerarFormaPagamentoBuilder()
            .build();
        this.formaPagamentoRepositoryJpa.saveAll(List.of(formaPagamentoUm, formaPagamentoDois));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
            Matchers.greaterThanOrEqualTo(2)))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(15)
    @DisplayName("Deletar - Fluxo Principal I - 204")
    void deveRetornarHttp204_quandoDeletar() throws Exception {
        var formaPagamentoSalva = CriadorDeBuilders.gerarFormaPagamentoBuilder()
                .build();
        formaPagamentoSalva = this.formaPagamentoRepositoryJpa.save(formaPagamentoSalva);

        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT.concat("/") + formaPagamentoSalva.getId()))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(16)
    @DisplayName("Deletar - Fluxo de Exceção I - id inexistente")
    void deveRetornarHttp404_quandoDeletar() throws Exception {
        var idInexistente = Math.round((Math.random() + 1) * 10000);

        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT.concat("/") + idInexistente))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
    }
}

