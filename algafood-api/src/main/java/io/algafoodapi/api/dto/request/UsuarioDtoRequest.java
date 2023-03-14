package io.algafoodapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "email")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UsuarioDtoRequest implements PoliticaDtoRequest, Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Length(max = 100)
    private String nome;

    @NotBlank
    @Email
    @Length(max = 150)
    private String email;

    @NotBlank
    @Length(min = 6, max = 100)
    private String senha;
}

