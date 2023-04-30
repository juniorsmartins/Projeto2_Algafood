package io.algafoodapi.business.model;

import io.algafoodapi.business.model.enuns.StatusPedidoEnum;
import jakarta.persistence.FetchType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class Pedido implements PoliticaEntidade<Long>, Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Transient
    private String nome;

    @Column(name = "subtotal", columnDefinition = "decimal(10.2)", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "taxa_frete", columnDefinition = "decimal(10.2) default 0.00", nullable = false)
    private BigDecimal taxaFrete = BigDecimal.ZERO;

    @Column(name = "valor_total", columnDefinition = "decimal(10.2)", nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(name = "data_hora_criacao", columnDefinition = "datetime", nullable = false)
    private OffsetDateTime dataHoraCriacao;

    @Column(name = "data_confirmacao", columnDefinition = "datetime")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "data_cancelamento", columnDefinition = "datetime")
    private OffsetDateTime dataCancelamento;

    @Column(name = "data_entrega", columnDefinition = "datetime")
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido", nullable = false)
    private StatusPedidoEnum statusPedido = StatusPedidoEnum.CRIADO;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itensPedido = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", referencedColumnName = "id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @Embedded
    private Endereco enderecoEntrega;
}

