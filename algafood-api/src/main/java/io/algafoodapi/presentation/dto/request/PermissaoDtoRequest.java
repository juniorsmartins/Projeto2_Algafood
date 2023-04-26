package io.algafoodapi.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class PermissaoDtoRequest implements PoliticaDtoRequest, Serializable {

  public static final long serialVersionUID = 1L;

  @NotBlank
  @Length(max = 100)
  private String nome;

  @NotBlank
  @Length(max = 250)
  private String descricao;
}

