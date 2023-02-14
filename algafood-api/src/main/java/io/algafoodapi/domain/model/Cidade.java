/**
 * @version 1.0.0
 * @since 01/01/2023
 *
 * @author Junior Martins
 * @link https://github.com/juniorsmartins/Projeto2_Algafood
 */

package io.algafoodapi.domain.model;

import io.algafoodapi.domain.core.validation.GruposValid;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.io.Serializable;

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
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "nome", length = 100, unique = true, nullable = false)
    private String nome;

    @Valid
    @ConvertGroup(from = Default.class, to = GruposValid.EstadoId.class)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", referencedColumnName = "id", nullable = false)
    private Estado estado;
}

