package io.algafoodapi.domain.model;

import io.algafoodapi.domain.model.enuns.StatusPedidoEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class Pedido implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subtotal", columnDefinition = "decimal(10.2)", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "taxa_frete", columnDefinition = "decimal(10.2) default 0.00")
    private BigDecimal taxaFrete = BigDecimal.ZERO;

    @Column(name = "valor_total", columnDefinition = "decimal(10.2)", nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "data_hora_criacao", nullable = false)
    private LocalDateTime dataHoraCriacao;

    @Column(name = "data_confirmacao")
    private LocalDateTime dataConfirmacao;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "status_pedido", nullable = false)
    private StatusPedidoEnum statusPedido;
}
