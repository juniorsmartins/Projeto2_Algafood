package io.algafoodapi.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ProdutoAtualizarDtoRequest implements PoliticaAtualizarDtoRequest<Long>, Serializable {

  private static final Long serialVersionUID = 1L;

  @NotNull
  @Positive
  private Long id;

  @NotBlank
  @Length(max = 100)
  private String nome;

  @Length(max = 250)
  private String descricao;

  @NotNull
  private BigDecimal preco;

  @NotNull
  private Boolean ativo;

  @NotNull
  @Valid
  private RestauranteIdDtoRequest restaurante;
}

