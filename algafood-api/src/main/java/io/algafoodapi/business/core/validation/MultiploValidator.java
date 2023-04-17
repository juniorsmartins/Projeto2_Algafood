package io.algafoodapi.business.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public final class MultiploValidator implements ConstraintValidator<MultiploAnotation, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(final MultiploAnotation constraintAnnotation) { // Inicializa a instância para chamadas futuras ao isValid
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(final Number value, final ConstraintValidatorContext constraintValidatorContext) { // Implementa a lógica de validação

        if (value == null) {
            return false;
        }

        var valorParaVerificarMultiplo = BigDecimal.valueOf(value.doubleValue());
        var valorContidoNaAnotacao = BigDecimal.valueOf(this.numeroMultiplo);
        var restoDaDivisao = valorParaVerificarMultiplo.remainder(valorContidoNaAnotacao); // Remainder retorna o resto da divisão (se o resto for zero, é múltiplo)

        return BigDecimal.ZERO.compareTo(restoDaDivisao) == 0;
    }
}

