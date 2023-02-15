package io.algafoodapi.domain.model;

import io.algafoodapi.domain.core.validation.GruposValid;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "estados")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = GruposValid.EstadoId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
}
