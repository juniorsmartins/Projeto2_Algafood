package io.algafoodapi.business.model;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissoes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Permissao implements PoliticaEntidade<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 250, nullable = false)
    private String descricao;
}
