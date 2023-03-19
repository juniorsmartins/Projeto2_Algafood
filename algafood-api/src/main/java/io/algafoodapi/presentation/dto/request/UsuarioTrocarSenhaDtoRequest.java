package io.algafoodapi.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public final class UsuarioTrocarSenhaDtoRequest implements PoliticaTrocarSenhaDtoRequest<Long, String>, Serializable {

  public static final Long serialVersionUID = 1L;

  @NotNull
  @Positive
  private Long id;

  @NotBlank
  @Length(min = 6, max = 100)
  private String senhaAtual;

  @NotBlank
  @Length(min = 6, max = 100)
  private String senhaNova;
}

