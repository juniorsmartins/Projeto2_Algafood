package io.algafoodapi.business.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE}) // Locais onde a anotação pode ser usada (Type permite em classe, interface e enum)
@Retention(RUNTIME) // Para o Bean Validation conseguir encontrar a anotação em tempo de execução
@Constraint(validatedBy = { FreteGratisObrigaDescricaoNoNomeValidator.class })
public @interface FreteGratisObrigaDescricaoNoNomeAnotation {

    String message() default "Taxa frete grátis requer descrição (Frete Grátis) obrigatória no nome. Esta descrição está ausente.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String valorTaxaFrete();

    String descricaoNome();

    String descricaoObrigatoria();
}

