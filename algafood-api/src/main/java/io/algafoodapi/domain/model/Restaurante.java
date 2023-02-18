package io.algafoodapi.domain.model;

import io.algafoodapi.domain.core.validation.FreteGratisObrigaDescricaoNoNomeAnotation;
import io.algafoodapi.domain.core.validation.GruposValid;
import io.algafoodapi.domain.core.validation.MultiploAnotation;
import io.algafoodapi.domain.core.validation.TaxaFreteAnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurantes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FreteGratisObrigaDescricaoNoNomeAnotation(valorTaxaFrete = "taxaFrete", descricaoNome = "nome", descricaoObrigatoria = "Frete Grátis")
public final class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @TaxaFreteAnotation
    @MultiploAnotation(numero = 5)
//    @PositiveOrZero(message = "{TaxaFrete.invalida}")
    @Column(name = "taxa_frete", columnDefinition = "decimal(10.2) default 0.00", nullable = false)
    private BigDecimal taxaFrete = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Valid
    @ConvertGroup(from = Default.class, to = GruposValid.CozinhaId.class)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", referencedColumnName = "id", nullable = false)
    private Cozinha cozinha;

    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurantes_formas_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    @Embedded
    private Endereco endereco;
}

