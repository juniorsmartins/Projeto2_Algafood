package io.algafoodapi.business.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurantes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public final class Restaurante implements PoliticaEntidade<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "taxa_frete", columnDefinition = "decimal(10.2) default 0.00", nullable = false)
    private BigDecimal taxaFrete = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "data_hora_utc_cadastro", columnDefinition = "datetime", updatable = false)
    private OffsetDateTime dataHoraUtcCadastro;

    @UpdateTimestamp
    @Column(name = "data_hora_utc_atualizacao", columnDefinition = "datetime")
    private OffsetDateTime dataHoraUtcAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", referencedColumnName = "id", nullable = false)
    private Cozinha cozinha;

    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurantes_formas_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    @Embedded
    private Endereco endereco;

    @Column(name = "ativo", columnDefinition = "boolean", nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @Column(name = "aberto", nullable = false)
    private Boolean aberto;

    public void ativar() {
        this.setAtivo(true);
    }

    public void inativar() {
        this.setAtivo(false);
    }
}

