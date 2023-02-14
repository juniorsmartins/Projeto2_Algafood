package io.algafoodapi.domain.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE}) // Locais onde a anotação pode ser usada (Type permite em classe, interface e enum)
@Retention(RUNTIME) // Para o Bean Validation conseguir encontrar a anotação em tempo de execução
@Constraint(validatedBy = { FreteGratisObrigaDescricaoNoNomeValidator.class })
public @interface FreteGratisObrigaDescricaoNoNomeAnotation {

    String message() default "descrição obrigatória inválida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String valorField();
    String descricaoField();
    String descricaoObrigatoria();
}

