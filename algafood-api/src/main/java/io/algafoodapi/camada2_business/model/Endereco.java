package io.algafoodapi.camada2_business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public final class Endereco {

    @Column(name = "endereco_cep", length = 15, nullable = false)
    private String cep;

    @Column(name = "endereco_bairro", length = 100, nullable = false)
    private String bairro;

    @Column(name = "endereco_logradouro", length = 100, nullable = false)
    private String logradouro;

    @Column(name = "endereco_numero", length = 10, nullable = false)
    private String numero;

    @Column(name = "endereco_complemento", length = 250)
    private String complemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_cidade_id")
    private Cidade cidade;
}

