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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public final class ProdutoDtoRequest implements PoliticaDtoRequest, Serializable {

  private static final Long serialVersionUID = 1L;

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

