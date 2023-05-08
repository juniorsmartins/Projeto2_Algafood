/**
 * @version 1.0.0
 * @since 01/01/2023
 *
 * @author Junior Martins
 * @link https://github.com/juniorsmartins/Projeto2_Algafood
 */

package io.algafoodapi.business.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "cidades")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codigo", length = 36, nullable = false)
    private String codigo;

    @Column(name = "nome", length = 100, unique = true, nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", referencedColumnName = "id", nullable = false)
    private Estado estado;

    @PrePersist
    private void gerarCodigo() {
        this.setCodigo(UUID.randomUUID().toString());
    }
}

