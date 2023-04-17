package io.algafoodapi.business.model;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "pratos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Prato implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "peso", nullable = false)
    private float peso;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "descricao", length = 250, nullable = true)
    private String descricao;

    @Column(name = "nome_autor", length = 150, nullable = true)
    private String nomeAutor;
}

