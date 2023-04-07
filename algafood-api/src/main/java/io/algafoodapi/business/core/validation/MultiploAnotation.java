package io.algafoodapi.business.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE}) // Locais onde a anotação pode ser usada
@Retention(RUNTIME) // Para o Bean Validation conseguir encontrar a anotação em tempo de execução
@Constraint(validatedBy = { MultiploValidator.class })
public @interface MultiploAnotation {

    String message() default "{Multiplo}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int numero();
}

