package io.algafoodapi.domain.core.validation;

import io.algafoodapi.domain.model.Restaurante;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public final class FreteGratisObrigaDescricaoNoNomeValidator implements ConstraintValidator<FreteGratisObrigaDescricaoNoNomeAnotation, Restaurante> {

    private String valorTaxaFrete;
    private String descricaoNome;
    private String descricaoObrigatoria;

    @Override
    public void initialize(final FreteGratisObrigaDescricaoNoNomeAnotation constraintAnnotation) {
        this.valorTaxaFrete = constraintAnnotation.valorTaxaFrete();
        this.descricaoNome = constraintAnnotation.descricaoNome();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(final Restaurante restaurante, final ConstraintValidatorContext constraintValidatorContext) {
        var isValido = true;

        try {
            var valorDoFrete = (BigDecimal) BeanUtils.getPropertyDescriptor(restaurante.getClass(), valorTaxaFrete)
                    .getReadMethod().invoke(restaurante);

            var descricao = (String) BeanUtils.getPropertyDescriptor(restaurante.getClass(), descricaoNome)
                    .getReadMethod().invoke(restaurante);

            if (valorDoFrete != null && BigDecimal.ZERO.compareTo(valorDoFrete) == 0 && descricao != null) {
                isValido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }

        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return isValido;
    }
}

