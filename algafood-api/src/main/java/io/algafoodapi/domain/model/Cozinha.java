package io.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonRootName("cozinha")
@Entity
@Table(name = "cozinhas")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Cozinha implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty("gastronomia")
    @Column(name = "nome", length = 80, nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha", fetch = FetchType.LAZY)
    private List<Restaurante> restaurantes = new ArrayList<>();
}
